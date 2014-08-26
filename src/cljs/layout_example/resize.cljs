(ns lt.layout.resize
  (:require [jayq.core :as jayq]))

(defn left [$resizable event]
  (let [$target (jayq/$ (aget event "target"))
        target-x (aget (.offset $target) "left")
        origin-x (+ (aget (.offset $resizable) "left")
                    (js/parseFloat (.css $resizable "width")))
        off-x (- origin-x (+ target-x (.-offsetX event)))]
    (.css $resizable "width" (str off-x "px"))))

(defn right [$resizable event]
  (let [$target (jayq/$ (aget event "target"))
        target-x (aget (.offset $target) "left")
        origin-x (aget (.offset $resizable) "left")
        off-x (- (+ target-x (.-offsetX event)) origin-x)]
    (.css $resizable "width" (str off-x "px"))))

(defn top [$resizable event]
  (let [$target (jayq/$ (aget event "target"))
        target-y (aget (.offset $target) "top")
        origin-y (+ (aget (.offset $resizable) "top")
                    (js/parseFloat (.css $resizable "height")))
        off-y (- origin-y (+ target-y (.-offsetY event)))]
    (.css $resizable "height" (str off-y "px"))))

(defn bottom [$resizable event]
  (let [$target (jayq/$ (aget event "target"))
        target-y (aget (.offset $target) "bottom")
        origin-y (aget (.offset $resizable) "bottom")
        off-y (- (+ target-y (.-offsetY event)) origin-y)]
    (.css $resizable "height" (str off-y "px"))))


(defn start [resize $resizable]
  (let [$container (jayq/parent $resizable)]
    (.on $container "mousemove.resize"
         #(resize $resizable %))
    ;; @FIXME OR mouseout.
    (.one $container "mouseup.resize"
          #(.off $container "mousemove.resize"))))
