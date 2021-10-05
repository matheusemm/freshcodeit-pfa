(ns freshcodeit-pfa.helpers
  (:require ["color-scheme" :as ColorScheme]))

(defn colors-hex
  []
  (-> (ColorScheme.)
      #_(.from_hue 21)
      (.scheme "triade")
      (.variation "soft")
      (.colors)))

(defn colors-rgb
  []
  (map (fn [hex] (->> hex
                      (partition 2)
                      (map #(js/parseInt (apply str %) 16))))
       (colors-hex)))
