(ns om-transition.examples.hello
  (:require
   [om-transition.core :refer [transition]]
   [om.core :as om :include-macros true]
   [om.dom :as dom :include-macros true]))

(enable-console-print!)

(defn hello
  [{:keys [v start time] :as data} owner]
  (om/component
    (dom/div nil
      (dom/p nil (str "v: " v))           ; v is time with easing function applied
      (dom/p nil (str "time: " time)))))  ; time is the current time at render

(def wrapper (.getElementById js/document "wrapper"))

(om/root transition {} {:target wrapper
                        :opts {:duration   1000
                               :autostart? true
                               :ease-fn    identity ; a.k.a. linear
                               :component  hello}})
