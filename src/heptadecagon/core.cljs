(ns heptadecagon.core
  (:require-macros [heptadecagon.macros :as macros])
  (:require [rum.core :as rum]))

(enable-console-print!)

(println (macroexpand-1 '(macroexpand-1 '(macros/convert "line"))))

;;(macros/convert "line")

(defn triangulate [scene P1 P2 P3]
  (let [[C1 C2] (repeatedly 2 #(name (gensym "C")))]
    (println C1 C2)
    (-> scene
        (.circle C1 P1 P2)
        (.circle C2 P2 P1)
        (.intersection P3 C1 C2))))

(defn perpendicular-bisector [scene P1 P2 P3' P4' L']
  (let [[C1 C2] (repeatedly 2 #(name (gensym "C")))]
    (-> scene
        (.circle C1 P1 P2)
        (.circle C2 P2 P1)
        (.intersection P3' C1 C2)
        (.intersection P4' C2 C1)
        (.line L' P3' P4'))))

(rum/defc heptadecagon < 
  {:did-mount (fn [] 
                (let [dim 500
                      scene (js/geom.Scene. #js{:left 0 :top 0 :right dim :bottom dim})
                      render 
                      (js/geom.renderer scene 
                                        (. js/document (getElementById "heptadecagon")))]
                  (-> scene
                    (.point "A" (/ (* 3 dim) 7) (/ dim 3))
                    (.point "O" (/ (* 5 dim) 7) (/ dim 3))
                    (.line "OA" "O" "A")
                    (perpendicular-bisector "O" "A" "B" "C" "BC")
                    #_(triangulate "O" "A" "D")
                    ;; (.segment "S" "A" "B")
                    ;;(.circle       "M" "O" "A")
                    ;;(.intersection "B" "M" "OA")
                    ;;(.circle       "N" "B" "A")
                    ;;(.intersection "C" "M" "N" 0)
                    ;;(.intersection "D" "M" "N" 1)
                    ;;(.line         "T" "A" "C")
                    ;;(.segment      "U" "A" "D")
                    ;;(.intersection "E" "T" "M" (.isnt scene "C"))
                    ;;(.segment      "V" "E" "B")
                    ;;(.intersection "F" "V" "U")
                    ;;(.segment      "W" "F" "C")
                    ;;(.intersection "W" "S"))
                  (render))))}
  []
  [:svg#heptadecagon.geometry-scene {:view-box "0 0 1000 1000"}])

(rum/mount (heptadecagon)
           (. js/document (getElementById "app")))

#_(defn on-js-reload []
  (swap! app-state update-in [:__figwheel_counter] inc))


