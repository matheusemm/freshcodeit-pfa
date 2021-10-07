(ns freshcodeit-pfa.components.month-spending
  (:require ["chart.js" :refer [Chart]]
            ["chartjs-gauge"]
            [reagent.core :as r]
            [freshcodeit-pfa.helpers :as helpers]))

(defn month-transactions
  [transactions ref-date]
  (filter #(= (helpers/date-to-month ref-date)
              (helpers/date-to-month (:date %)))
          transactions))

(defn chart-data
  [transactions]
  (.log js/console transactions)
  (let [total (->> transactions (map :amount) (apply +))]
    (clj->js {:type "gauge"
              :data {:datasets [{:value total
                                 :minValue 0
                                 :data [100 200 300 400 500 600]
                                 :backgroundColor {"green" "yellow" "orange" "red" "blue" "gray"}}]}})))

(defn display-chart
  [transactions]
  (let [context (.getElementById js/document "month-spending-canvas")
        data (-> transactions
                 (month-transactions (helpers/today))
                 chart-data)]
    (Chart. context data)))


(defn month-spending
  [transactions]
  (let [chart (atom nil)]
    (r/create-class
     {:display-name "month-spending"
      :reagent-render (fn [transactions] [:canvas#month-spending-canvas])
      :component-did-mount (fn [this] (reset! chart (display-chart transactions)))
      :component-did-update (fn [this _]
                              (.destroy @chart)
                              (reset! chart (display-chart (second (r/argv this)))))})))
