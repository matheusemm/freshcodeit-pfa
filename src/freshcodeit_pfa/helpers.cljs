(ns freshcodeit-pfa.helpers
  (:require ["color-scheme" :as ColorScheme]
            [tick.core :as tick]))

;; Colors

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

;; Date

(def months
  {tick/JANUARY 1
   tick/FEBRUARY 2
   tick/MARCH 3
   tick/APRIL 4
   tick/MAY 5
   tick/JUNE 6
   tick/JULY 7
   tick/AUGUST 8
   tick/SEPTEMBER 9
   tick/OCTOBER 10
   tick/NOVEMBER 11
   tick/DECEMBER 12})

(defn today
  []
  (tick/today))

(defn date-to-str
  [date]
  (let [day (tick/day-of-month date)
        day (if (< day 10) (str "0" day) day)]
    (str (tick/year date) "-" (months (tick/month date)) "-" day)))

(defn date-to-month
  [date]
  (str (tick/year date) "-" (months (tick/month date))))
