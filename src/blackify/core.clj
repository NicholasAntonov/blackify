(ns blackify.core
  (:use mikera.image.colours)
  (:use mikera.image.core)
  (:use mikera.image.filters)
  (:use mikera.image.spectrum)
  (:require [clojure.java.io :refer [resource]])
  (:import [java.awt Color])
  (:gen-class))

(defn black-or-white
  [color]
  (.getRGB (if (> (.getGreen (new Color color)) (* 0.9 255))
    Color/BLACK
    Color/WHITE)))

(defn blackify
  []
  (let [count ((grayscale) (-> "count.png" clojure.java.io/file load-image))]
    ;;(set-pixels count (map black-or-white (get-pixels count)))
    (show count :title "foobar")))

(defn newImage
  []
  ;; create a new image
  (let [bi (new-image 32 32)]

    ;; gets the pixels of the image, as an int array
    (def pixels (get-pixels bi))

    ;; fill some random pixels with colours
    (dotimes [i 1024]
      (aset pixels i (rand-colour)))

    ;; update the image with the newly changed pixel values
    (set-pixels bi pixels)

    (show bi)

    (save bi "bar.png" :quality 0.9 :progressive true)))

(defn -main
  "I don't do a whole lot ... yet."
  [threshold & args]
  (println "Hello, World!")
  (println threshold)
  (blackify))
