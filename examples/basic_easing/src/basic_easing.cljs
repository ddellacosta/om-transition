(ns om-transition.examples.basic-easing
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require
   [om-transition.core :refer [transition]]
   [om-transition.easing :refer [sine-out]]
   [om.core :as om :include-macros true]
   [sablono.core :include-macros true :refer [html]]
   [cljs.core.async :refer [chan sub <! map< put!]]))

(enable-console-print!)

(defn simple
  [{:keys [v start time] :as data} owner]
  (reify
    om/IRenderState
    (render-state [_ {:keys [control-ch] :as state}]
      (html
       [:div
        [:button
         {:onClick #(put! control-ch true)}
         "Start the animation!"]
        [:div "Time: " time]
        [:div "V: " v]
        [:svg
         [:g
          [:circle {:cx (+ 10 (* v 200)) :cy 30 :r 10 :fill "red"}]]]]))))

(def wrapper (.getElementById js/document "wrapper"))

(om/root transition {} {:target wrapper
                        :opts {:duration 1000
                               :ease-fn sine-out
                               :component simple}})
