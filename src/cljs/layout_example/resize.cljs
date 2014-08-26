(ns lt.layout.resize
  (:require [jayq.core :as jayq]))

(defn real-width
  ([$el val] (.css $el "width" (str val "px")))
  ([$el] (js/parseFloat (.css $el "width"))))

(defn real-height
  ([$el val] (.css $el "height" (str val "px")))
  ([$el] (js/parseFloat (.css $el "height"))))

(defn left [$resizable event]
  (let [$target (jayq/$ (aget event "target"))
        target-x (aget (.offset $target) "left")
        origin-x (+ (aget (.offset $resizable) "left")
                    (real-width $resizable))
        off-x (- origin-x (+ target-x (.-offsetX event)))]
    (real-width $resizable off-x)))

(defn right [$resizable event]
  (let [$target (jayq/$ (aget event "target"))
        target-x (aget (.offset $target) "left")
        origin-x (aget (.offset $resizable) "left")
        off-x (- (+ target-x (.-offsetX event)) origin-x)]
    (real-width $resizable off-x)))

(defn top [$resizable event]
  (let [$target (jayq/$ (aget event "target"))
        target-y (aget (.offset $target) "top")
        origin-y (+ (aget (.offset $resizable) "top")
                    (real-height $resizable))
        off-y (- origin-y (+ target-y (.-offsetY event)))]
    (real-height $resizable off-y)))

(defn bottom [$resizable event]
  (let [$target (jayq/$ (aget event "target"))
        target-y (aget (.offset $target) "bottom")
        origin-y (aget (.offset $resizable) "bottom")
        off-y (- (+ target-y (.-offsetY event)) origin-y)]
    (real-height $resizable off-y)))


(defn start [resize $resizable]
  (let [$container (jayq/parent $resizable)]
    (.on $container "mousemove.resize"
         #(resize $resizable %))
    ;; @FIXME OR mouseout.
    (.one $container "mouseup.resize"
          #(.off $container "mousemove.resize"))))
