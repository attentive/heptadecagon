(ns heptadecagon.core
  (:require [rum.core :as rum]))

(enable-console-print!)

(rum/defc heptadecagon < 
  {:did-mount (fn [] 
                (let [dim 500
                      scene (js/geom.Scene. #js{:left 0 :top 0 :right dim :bottom dim})
                      render 
                      (js/geom.renderer scene 
                                        (. js/document (getElementById "heptadecagon")))]
                  (-> scene
                    (.point "A" (/ (* 3 dim) 7) (/ dim 3))
                    (.point "B" (/ (* 5 dim) 7) (/ dim 3))
                    (.segment "S" "A" "B")
                    (.circle       "M" "A" "B")
                    (.circle       "N" "B" "A")
                    (.intersection "C" "M" "N" 0)
                    (.intersection "D" "M" "N" 1)
                    (.line         "T" "A" "C")
                    (.segment      "U" "A" "D")
                    (.intersection "E" "T" "M" (.isnt scene "C"))
                    (.segment      "V" "E" "B")
                    (.intersection "F" "V" "U")
                    (.segment      "W" "F" "C")
                    (.intersection "W" "S"))
                  (render)))}
  []
  [:svg#heptadecagon.geometry-scene {:view-box "0 0 1000 1000"}])

(rum/mount (heptadecagon)
           (. js/document (getElementById "app")))

#_(defn on-js-reload []
  (swap! app-state update-in [:__figwheel_counter] inc))


