(ns lt.layout.core
  (:require [crate.core :as crate]
            [jayq.core :as jayq]
            [lt.layout.resize :as resize])
  (:require-macros [crate.def-macros :refer [defpartial]]
                   [jayq.macros :refer [ready]]))

(defpartial resize-handle [dir]
  [:div {:class (str "resize-handle " (name dir))}])

(defn resizer [dir]
  (condp = dir
    :left resize/left
    :right resize/right
    :top resize/top
    :bottom resize/bottom))

(defn add-resize-handle [el dir]
  (let [resize (resizer dir)
        handle (resize-handle dir)
        $handle (jayq/$ handle)
        $el (jayq/$ el)
        ]
    (jayq/append $el handle)
    (.on $handle "mousedown.resize"
         #(resize/start resize $el))))

(ready
 (def horizontal-resizables (jayq/$ ".container.horizontal > .resizable"))
 (dorun (map #(add-resize-handle % :left) horizontal-resizables))
 (dorun (map #(add-resize-handle % :right) horizontal-resizables))

 (def vertical-resizables (jayq/$ ".container.vertical > .resizable"))
 (dorun (map #(add-resize-handle % :top) vertical-resizables))
 (dorun (map #(add-resize-handle % :bottom) vertical-resizables)))


