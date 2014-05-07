# om-transition

`om-transition` is a simple Om component which takes a set of options and another Om component as arguments, and it injects simple timing information into that component (via its cursor) so that it can...do whatever it would like to do.

`om-transition` relies upon the fact that Om itself renders with [`requestAnimationFrame`](https://developer.mozilla.org/en/docs/Web/API/window.requestAnimationFrame) (with a fallback to `setTimeout`), and leverages this by simply setting the time and easing value to trigger a re-render, and letting Om (and React) do the hard work.


## Usage

Lein config:

```clojure
[om-transition "0.0.1"]
```

`om-transition` is just an Om component with a few extra args, which injects a few values into the wrapped component's passed cursor.

```clojure
(ns my-app
  (:require
   [om-transition.core :refer [transition]]
   [om.core :as om :include-macros true]
   [om.dome :as dom :include-macros true]))

(defn my-component
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
                               :component  my-component}})
```


## Examples

For now see examples directory: hello (same as above) and simple easing; more TODO


## Copyright and License

Copyright 2014 Dave Della Costa

Released under the [Eclipse Public License](http://www.eclipse.org/legal/epl-v10.html).

Thanks to Daniel Kersten for `om-transition.easing` code taken from [ominate](https://github.com/danielytics/ominate).
