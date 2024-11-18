# Analiza utilizării de design patterns într-o aplicație de recenzii pentru jocuri video

Scopul proiectului este dea a dezvolta un sistem de recenzii pentru jocuri video, similar cu servicii precum IMDB sau Metacriticm unde
utilizatorii pot acorda note și recenzii jocurilor din baza de date a sistemului, pot să țină evidența jocurilor pe bază de progres (Now Playing, Completed, Drop),
să își creeze o pagină dedicată cu colecția lor pe care o poate customiza cu teme și să poată căuta jocuri.
Sistemul oferă recomandări pe baza jocurilor din lista personală a utilizatorului
și oferă un top al celor mai populare jocuri pe baza scorurilor oferite de utilizatori
De asemenea, Administratorii pot gestiona direct din sistem toate resursele unui joc (Titlu, genre, poze/screenshots cu gameplay, trailere).

Proiectul va implementa diverse deisgn patterns pentru a analiza performanța, scalabilitatea și posibilitatea de extindere a codului sursă.

# Detalii Implementare

**Arhitectură MVC** \
**Frontend:** Angular \
**Backend:** Spring Boot plus Java

Backendul va fi dezvoltat într-o variantă inițială ce va utiliza anti-patternuri și cod ineficient
O a doua variantă a backendului va fi dezvoltată utilizând design patterns pentru a putea analiza performanța dintre cele doua sisteme, dar și scalabilitatea, lizibilitatea codului cât și ușurința cu care el va putea fi extins pe viitor
Database: Oracle SQL

# Obiective

Analiza design patterns și anti-patterns:
* Evaluarea impactului design patterns-urilor asupra performanței, ușurinței de extindere și mentenabilității codului
* Identificarea anti-patterns frecvente și propunerea de soluții pentru evitarea lor

Asigurarea unei metodologii clare de testare:
* Definirea unor scenarii de testare realiste pentru încărcare, stres și utilizare zilnică
* Utilizarea unor benchmark-uri standard pentru rezultate obiective și replicabile

Analiza performanței sistemului:
* Măsurarea timpului de răspuns pentru cereri individuale și cereri simultane
* Identificarea și rezolvarea bottleneck-urilor în aplicație

Calitatea codului:
* Asigurarea unui stil uniform de codare conform convențiilor
* Reducerea codului duplicat și îmbunătățirea modularității
* Validarea extensibilității pentru viitoare funcționalități
* Recomandări viitoare pentru utilizarea de design patterns (unde au sens)
