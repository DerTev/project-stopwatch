(ns project-stopwatch.core
  (:require
   [reagent.core :as r]
   [reagent.dom :as d]
   [clojure.core.async :as async]
   [project-stopwatch.utils.file :as file]))

(def data (r/atom [{:project "Test" :date "20.07.2022" :time "1" :fee "100"}]))

(defn save
  "Write data to data-file"
  []
  ; TODO
  )

(defn backup
  "Create backup from data.json"
  []
  ; TODO
  )

; Init app
(defn mount-root []
  (d/render [:div [:div {:class ["header"]}
              [:h1 "Project Stopwatch"]
              (if (boolean (.-chrome js/window)) [:div {:id "actions"} 
                                                  [:i {:onClick #() :class "fa-solid fa-folder-open" :style {:cursor "pointer"}}]
                                                  [:i {:onClick #(backup) :class "fa-solid fa-folder-tree ms-2" :style {:cursor "pointer"}}]
                                                  [:i {:onClick #(save) :class "fa-solid fa-floppy-disk ms-2" :style {:cursor "pointer"}}]])]
             [:br]
             [:div {:id ["main"]}
              (if (boolean (.-chrome js/window))
                [:table {:class "table"}
                 [:thead
                  [:tr
                   [:th {:scope "col"} "Projekt"]
                   [:th {:scope "col"} "Datum"]
                   [:th {:scope "col"} "Zeit"]
                   [:th {:scope "col"} "Honorar"]
                   [:th {:scope "col"} "Aktionen"]]]
                 [:tbody
                   (for [session @data]
                     [:tr [:td (get session :project)]
                      [:td (get session :date)]
                      [:td (get session :time)]
                      [:td (get session :fee)]
                      [:td [:i {:onClick (js/alert "Coming soon") :class "fa-solid fa-trash" :style {:cursor "pointer"}}]]])]]
                [:p "Bitte öffnen Sie diese Seite mit einem " [:a {:href "https://en.wikipedia.org/wiki/Chromium_(web_browser)#Active" :target :blank} "chromium basierenden Browser"] "!"])]] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
