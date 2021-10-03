(ns freshcodeit-pfa.state
  (:require [reagent.core :as r]))

(def transactions (r/atom {1 {:id 1
                              :date "2021-10-03"
                              :payee "Bauhaus"
                              :amount 130}}))
