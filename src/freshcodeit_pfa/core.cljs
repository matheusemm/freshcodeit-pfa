(ns freshcodeit-pfa.core
    (:require [freshcodeit-pfa.state :as state]
              [freshcodeit-pfa.components.monthly-costs :refer [monthly-costs]]
              [freshcodeit-pfa.helpers :as helpers]
              [reagent.core :as r]
              [reagent.dom :as d]
              [clojure.string :as str]
              [tick.core :as tick]))

(defn add-transaction
  [values]
  (let [id (random-uuid)
        {:keys [date payee amount]} @values]
    (swap! state/transactions assoc id {:id id
                                        :date (tick/date date)
                                        :payee (str/trim payee)
                                        :amount (js/parseInt amount)})
    (reset! values {:date "" :payee "" :amount "0"})))


(defn delete-transaction
  [id]
  (swap! state/transactions dissoc id))

(defn transactions
  []
  (let [add-values (r/atom {:date "" :payee "" :amount 0})]
    (fn []
      [:div.transactions
       [:h1.display-4 "Transactions"]
       (let [{:keys [date payee amount]} @add-values]
         [:div.row.pb-4.pt-4.align-items-end
          [:div.col
           [:label.form-label {:for "date"} "Date"]
           [:input#date.form-control.form-control-sm
            {:type :date
             :value date
             :on-change #(swap! add-values assoc :date (.. % -target -value))}]]
          [:div.col
           [:label.form-label {:for "payee"} "Payee"]
           [:input#payee.form-control.form-control-sm
            {:type :text
             :value payee
             :on-change #(swap! add-values assoc :payee (.. % -target -value))}]]
          [:div.col
           [:label.form-label {:for "amount"} "Amount"]
           [:input#amount.form-control.form-control-sm
            {:type :number
             :value amount
             :on-change #(swap! add-values assoc :amount (.. % -target -value))}]]
          [:div.col
           [:button.btn.btn-primary.btn-sm.float-end
            {:on-click #(add-transaction add-values)}
            "Add"]]])

       [:div.row.pb-4
        [:div.col
         [:table.table.table-striped
          [:thead.table-light
           [:tr
            [:th "Date"]
            [:th "Payee"]
            [:th "Amount"]
            [:th ""]]]
          [:tbody
           (let [transactions (vals @state/transactions)]
             (if (seq transactions)
               (for [{:keys [id date payee amount]} transactions]
                 [:tr.align-baseline {:key id}
                  [:td (helpers/date-to-str date)]
                  [:td payee]
                  [:td (str "\u20B4" amount)]
                  [:td
                   [:button.btn.btn-link.text-danger
                    {:on-click #(delete-transaction id)}
                    [:i.bi.bi-trash]]]])))]]]]

       [:div.row
        [:div.col
         [monthly-costs (vals @state/transactions)]]]])))

(defn mount-root []
  (d/render [transactions] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
