(ns project-stopwatch.core
  (:require
   [reagent.core :as r]
   [reagent.dom :as d]
   [clojure.core.async :as async]
   [project-stopwatch.utils.file :as file]))

(def data (r/atom [{:project "Test" :date "20.07.2022" :time "1" :fee "100"}]))

(defn load
  "Load data"
  []
  ; TODO
  )

(defn save
  "Save data"
  []
  ; TODO
  )

(defn backup
  "Backup data"
  []
  ; TODO
  )

; Init app
(defn mount-root []
  (d/render [:div [:div {:class ["header"]}
                   [:h1 "Project Stopwatch"]
                   (if (boolean (.-chrome js/window)) [:div {:id "actions"}
                                                       [:i {:onClick #(load) :class "fa-solid fa-folder-open" :style {:cursor "pointer"}}]
                                                       [:i {:onClick #(backup) :class "fa-solid fa-folder-tree ms-2" :style {:cursor "pointer"}}]])]
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
                  (for [[id session] (map vector (range) @data)]
                    [:tr [:td (get session :project)]
                     [:td (get session :date)]
                     [:td (get session :time)]
                     [:td (get session :fee)]
                     [:td [:i {:onClick #(into (subvec % 0 id) (subvec % (inc id))) :class "fa-solid fa-trash" :style {:cursor "pointer"}}]]])]]
                [:p "Bitte öffnen Sie diese Seite mit einem " [:a {:href "https://en.wikipedia.org/wiki/Chromium_(web_browser)#Active" :target :blank} "chromium basierenden Browser"] "!"])]] (.getElementById js/document "app"))
                
                ; Enable Auto-Save
                (add-watch data :auto-save save))

(defn ^:export init! []
  (mount-root))
