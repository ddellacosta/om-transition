(ns om-transition.core
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require
   [om.core :as om :include-macros true]
   [om.dom :as dom :include-macros true]
   [cljs.core.async :refer [chan sub <! map< put!]]))

(enable-console-print!)

(defn transition
  [data owner {:keys [duration ease-fn component autostart?]}]
  (reify
    om/IInitState
    (init-state [_]
      {:control-ch (chan)
       :duration   duration
       :in-transition? false})

    om/IWillMount
    (will-mount [_]
      (let [control-ch (om/get-state owner :control-ch)]
        (go-loop []
          (let [start? (<! control-ch)]
            (if start?
              (do
                (om/update! data :start (.now js/Date))
                (om/set-state! owner :in-transition? true))
              (om/set-state! owner :in-transition? false))))))

    om/IDidMount
    (did-mount [_]
      (when autostart?
        (put! (om/get-state owner :control-ch) true)))

    om/IDidUpdate
    (did-update [_ _ _]
      (let [{:keys [v start]} data
            now     (.now js/Date)
            elapsed (- now start)
            vinterp (/ elapsed duration)]
        (if (om/get-state owner :in-transition?)
          (if (> now (+ start duration))
            (om/set-state! owner :in-transition? false)
            (om/transact! data #(assoc % :v (ease-fn vinterp) :time now))))))

    om/IRenderState
    (render-state [_ state]
      (om/build component data {:state state}))))
