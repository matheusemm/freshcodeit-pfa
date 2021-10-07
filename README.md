
# FRESHCODE PFA - Personal Finance Application

Simple personal expenses tracking application written in ClojureScript and
reagent.

## Requirements

* [x] Use [ClojureScript](https://clojurescript.org/) plus any library for HTML ([reagent](https://reagent-project.github.io/)).
* The page should contain:
  * [x] table of transactions with fields: who was paid and how much
  * [x] 2 input fields for creating a new transaction
* [x] A graph that shows costs by month
* [ ] A separate gauge widget that shows the total spending for the current month

### "Plus" Requirements

* [x] Custom code to manage state (reagent's atoms)
* [x] One-way dataflow and concentration of logic in one place
  * reagent leads naturally to one-way dataflow with its atoms
  * I could also understand "concentration of logic in one place" as, e.g., maintaining transaction functions in its namespace (`transaction.cljs`), but I kept the code spread through namespaces (`month-spending`, `monthly-costs`).
* [ ] Link to working application
* [ ] Application deployed to the online service [repl.it](https://replit.com/) or similar
  * I've never used repl.it before, so I am not sure if what I did is what was expected, probably not. Then I decided to not check this item. I think this is the link to the published project: https://replit.com/@MatheusEduardoE/freshcodeit-pfa?v=1.

## Decisions

* I already knew a bit of reagent, so decided to use it instead of other similar frameworks.
* I decided to use [tick](https://github.com/juxt/tick) for date manipulation but if I had more time I would probably try to find other library because I found tick a bit cumbersome.
* I couldn't finish the gauge widget :-( but I kept the code as reference.
