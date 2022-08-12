(ns project-stopwatch.core
  (:require
   [reagent.core :as r]
   [reagent.dom :as d]
   [clojure.core.async :as async]
   [cljs.core.async.interop :refer [<p!]]
   [project-stopwatch.utils.file :as file]))

(def directory (r/atom nil))
;(def project-filter (r/atom nil))

(defn save
  "Write data to data-file"
  []
  (file/write-file true
                   (file/open-file-from-dir true @directory "data.json")
                   "lel"))

(defn create-backup
  "Create backup from data.json"
  []
  ;TODO
  )

(defn update-main
  "Update Main-Screen"
  []
  (async/go
    (println (file/read-file (file/open-file-from-dir true @directory "data.json")))
    (let [data nil]
      (d/render [:table {:class "table"}
                 [:thead
                  [:tr
                   [:th {:scope "col"} "Projekt"]
                   [:th {:scope "col"} "Datum"]
                   [:th {:scope "col"} "Zeit"]
                   [:th {:scope "col"} "Honorar"]
                   [:th {:scope "col"} "Aktionen"]]]
                 [:tbody
                  [:tr
                   [:td "Test"]
                   [:td "20.07.2022"]
                   [:td "1"]
                   [:td "100"]
                   [:td [:i {:class "fa-solid fa-trash" :style {:cursor "pointer"}}]]]]] (.getElementById js/document "main")))))

(defn open-dir
  "Open new directory"
  []
  (async/go (reset! directory (async/<! (file/open-dir true)))
            (update-main)))

; Init app
(defn mount-root []
  (d/render [:div [:div {:class ["header"]}
              [:h1 "Project Stopwatch"]
              (if (boolean (.-chrome js/window)) [:div {:id "actions"} 
                                                  [:i {:onClick open-dir :class "fa-solid fa-folder-open" :style {:cursor "pointer"}}] 
                                                  [:i {:onClick #(.reload js/location) :class "fa-solid fa-folder-closed ms-2" :style {:cursor "pointer"}}]
                                                  [:i {:onClick #(js/alert "Backups werden in der aktuellen Version dieser App leider noch nicht unterstützt.") :class "fa-solid fa-folder-tree ms-2" :style {:cursor "pointer"}}]
                                                  [:i {:onClick update-main :class "fa-solid fa-arrows-rotate ms-2" :style {:cursor "pointer"}}]])]
             [:br]
             [:div {:id ["main"]}
              (if (boolean (.-chrome js/window))
                [:p "Es wurde noch kein Ordner geöffnet." [:br] "Bitte öffnen Sie einen Ordner, um zu starten!"]
                [:p "Bitte öffnen Sie diese Seite mit einem " [:a {:href "https://en.wikipedia.org/wiki/Chromium_(web_browser)#Active" :target :blank} "chromium basierenden Browser"] "!"])]] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
