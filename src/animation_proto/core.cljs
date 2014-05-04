(ns animation-proto.core
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require
   [om.core :as om :include-macros true]
   [sablono.core :refer [html] :include-macros true]
   [ominate.easing :as ease :refer [sine-in sine-out]]
   [cljs.core.async :refer [chan sub <! map< put!]]))

(enable-console-print!)

(defn some-animation-component
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

(defn animator
  [data owner {:keys [duration ease-fn animation-comp]}]
  (reify
    om/IInitState
    (init-state [_]
      {:control-ch (chan)
       :duration   duration
       :animating? false})

    om/IWillMount
    (will-mount [_]
      (let [control-ch (om/get-state owner :control-ch)]
        (go-loop []
          (let [start? (<! control-ch)]
            (if start?
              (do
                (om/update! data :start (.now js/Date))
                (om/set-state! owner :animating? true))
              (om/set-state! owner :animating? false))))))

    om/IDidUpdate
    (did-update [_ _ _]
      (let [{:keys [v start]} data
            now     (.now js/Date)
            elapsed (- now start)
            vinterp (/ elapsed duration)]
        (if (om/get-state owner :animating?)
          (if (> now (+ start duration))
            (om/set-state! owner :animating? false)
            (om/transact! data #(assoc % :v (ease-fn vinterp) :time now))))))

    om/IRenderState
    (render-state [_ state]
      (html
       (om/build animation-comp data {:state state})))))

(def wrapper (.getElementById js/document "wrapper"))

(om/root animator {}
         {:target wrapper
          :opts {:duration 1000
                 :ease-fn sine-out
                 :animation-comp some-animation-component}})
