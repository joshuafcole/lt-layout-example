(ns lt.layout.resize
  (:require [jayq.core :as jayq]))

(defn real-width
  ([$el val] (.css $el "width" (str val "px")))
  ([$el] (js/parseFloat (.css $el "width"))))

(defn real-height
  ([$el val] (.css $el "height" (str val "px")))
  ([$el] (js/parseFloat (.css $el "height"))))

(defn left [$resizable event]
  (let [origin-x (+ (aget (.offset $resizable) "left")
                    (real-width $resizable))
        off-x (- origin-x (.-pageX event))]
    (real-width $resizable off-x)))

(defn right [$resizable event]
  (let [origin-x (aget (.offset $resizable) "left")
        off-x (- (.-pageX event) origin-x)]
    (real-width $resizable off-x)))

(defn top [$resizable event]
  (let [origin-y (+ (aget (.offset $resizable) "top")
                    (real-height $resizable))
        off-y (- origin-y (.-pageY event))]
    (real-height $resizable off-y)))

(defn bottom [$resizable event]
  (let [origin-y (aget (.offset $resizable) "bottom")
        off-y (- (.-pageY event) origin-y)]
    (real-height $resizable off-y)))

(defn start [resize $resizable]
  (let [$container (jayq/parent $resizable)]
    (.on $container "mousemove.resize"
         #(resize $resizable %))
    (.one $container "mouseup.resize"
          (fn []
            (.off $container "mousemove.resize")
            (.off $container "mouseout.resize")))
    (.one $container "mouseleave.resize"
          (fn []
            (.off $container "mousemove.resize")
            (.off $container "mouseup.resize")))))
