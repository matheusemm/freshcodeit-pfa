(ns freshcodeit-pfa.components.monthly-costs
  (:require [reagent.core :as r]
            [goog.string :as gstr]
            [goog.string.format]))

(defn costs-by-month
  [transactions]
  (reduce (fn [costs {:keys [date amount]}]
            (let [month (subs date 0 7)]
              (update costs month + amount)))
          {}
          transactions))

(defn chart-data
  [costs]
  (clj->js {:type "bar"
            :data {:labels (vec (keys costs))
                   :datasets [{:label "Total Cost"
                               :data (vec (vals costs))}]}
            :options {:scales {:y {:beginAtZero true}}}}))

(defn display-chart
  [transactions]
  (let [context (.getElementById js/document "monthly-costs-canvas")
        data (-> transactions costs-by-month chart-data)]
    (js/Chart. context data)))

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
