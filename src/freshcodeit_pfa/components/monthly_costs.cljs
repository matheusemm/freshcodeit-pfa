(ns freshcodeit-pfa.components.monthly-costs
  (:require [reagent.core :as r]
            [goog.string :as gstr]
            [goog.string.format]
            ["chart.js" :refer [Chart]]
            [freshcodeit-pfa.helpers :as helpers]))

(defn costs-by-month
  [transactions]
  (reduce (fn [costs {:keys [date amount]}]
            (let [month (helpers/date-to-month date)]
              (update costs month + amount)))
          {}
          transactions))

(defn chart-colors
  []
  (let [colors (helpers/colors-rgb)]
    {:borders (mapv #(apply gstr/format "rgb(%s, %s, %s)" %) colors)
     :backgrounds (mapv #(apply gstr/format "rgba(%s, %s, %s, 0.2)" %) colors)}))

(defn chart-data
  [costs]
  (let [{:keys [borders backgrounds]} (chart-colors)]
    (clj->js {:type "bar"
              :data {:labels (vec (keys costs))
                     :datasets [{:label "Total Cost"
                                 :data (vec (vals costs))
                                 :backgroundColor backgrounds
                                 :borderColor borders
                                 :borderWidth 1}]}
              :options {:scales {:y {:beginAtZero true}}}})))

(defn display-chart
  [transactions]
  (let [context (.getElementById js/document "monthly-costs-canvas")
        data (-> transactions costs-by-month chart-data)
        _ (.log js/console data)]
    (Chart. context data)))

(defn monthly-costs
  [transactions]
  (let [chart (atom nil)]
    (r/create-class
     {:display-name "monthly-costs"
      :reagent-render (fn [transactions] [:canvas#monthly-costs-canvas])
      :component-did-mount (fn [this] (reset! chart (display-chart transactions)))
      :component-did-update (fn [this _]
                              (.destroy @chart)
                              (reset! chart (display-chart (second (r/argv this)))))})))
