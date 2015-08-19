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
  (.getRGB (if (> (.getGreen (new Color color)) (* 0.7 255))
    Color/BLACK
    Color/WHITE)))

(defn blackify
  []
  (let [count (-> "count.png" clojure.java.io/file load-image)]
    (set-pixels count
      (into-array Integer/TYPE
        (map black-or-white (get-pixels ((grayscale) count)))))
    (show count :title "foobar")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (println (first args))
  (blackify))
