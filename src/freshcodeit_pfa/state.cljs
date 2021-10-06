(ns freshcodeit-pfa.state
  (:require [reagent.core :as r]
            [tick.core :as tick]))

(def transactions (r/atom {1 {:id 1
                              :date (tick/today)
                              :payee "Bauhaus"
                              :amount 130}}))
