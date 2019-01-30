<h4>Wymagania funkcjonalne:</h4>
<ul>
<li>Gość powinien mieć możliwość czytania informacji dodanych przez bibliotekarza.</li>
<li>Gość powinien mieć możliwość rejestracji w bibliotece.</li>
<li>Gość powinien mieć możliwość logowania/wylogowania w bibliotece.</li>
<li>Gość powinien mieć możliwość przeglądania listy książek dostępnych w bibliotece.</li>
<li>Gość powinien mieć możliwość wyszukiwania książek po następujących elementach: tytuł, autor, wydawnictwo, rok wydania.</li>
<li>Użytkownik powinien mieć możliwość przeglądania listy książek dostępnych w bibliotece.</li>
<li>Użytkownik powinien mieć możliwość wyszukiwania książek po następujących elementach: tytuł, autor, wydawnictwo, rok wydania.</li>
<li>Użytkownik powinien mieć możliwość wypożyczenia książki z biblioteki.</li>
<li>Użytkownik powinien mieć możliwość przeglądania listy wypożyczonych książek.</li>
<li>Bibliotekarz powinien mieć możliwość dodawania nowej książki do kolekcji.</li>
<li>Bibliotekarz powinien mieć możliwość usunięcia książki z biblioteki.</li>
<li>Bibliotekarz powinien mieć możliwość modyfikacji książki w bibliotece.</li>
<li>Bibliotekarz powinien mieć możliwość zablokowania użytkownika.</li>
</ul>

<h4>Wymagania niefunkcjonalne:</h4>
<ul>
<li>System musi działać stabilnie.</li>
<li>System musi być odporny na popularne błędy użytkowników.</li>
<li>System powinien wypożyczyć jeden egzemplarz książki dokładnie jednemu użytkownikowi.</li>
<li>Użytkownik może wypożyczyć maksymalnie 7 książek.</li>
<li>System musi być elastyczny, tj. umożliwiać rozszerzanie o dodatkowe moduły w przyszłości.</li>
<li>System musi być testowalny w celu wykrycia ewentualnych błędów programistycznych.</li>
<li>System musi być dostępny z poziomu różnych przeglądarek użytkownika.</li>
<li>System musi być intuicyjny („user-friendly”), posiadać prosty i wygodnym interfejsem użytkownika.</li>
<li>System musi być zabezpieczony przed popularnymi atakami typu CSRF, XSS, Session Hijacking czy SQL Injection.</li>
<li>System musi być zoptymalizowany.</li>
<li>System musi być odporny na ewentualne awarie.</li>
<li>System musi być zabezpieczony przed nieautoryzowanym dostępem.</li>
<li>Wymagania dotyczące ograniczeń systemu:</li>
<li>Front-end aplikacji będzie wykonany przy pomocy technologii HTML5, CSS3, JS.</li>
<li>Back-end systemu zostanie wykonany w języku programowania Java.</li>
<li>System nie będzie posiadał wsparcia technicznego, wszelka pomoc będzie dostępna w dokumentacji.</li>
<li>System będzie zoptymalizowany do stopnia zapewniającego relatywnie szybki czas reakcji przy stosunkowo niewielkim obciążeniu serwera.</li>
<li>System uruchomi się na serwerze obsługującym język programowania Java oraz system relacyjnej bazy danych.</li>
</ul>

<h4>Wymagania dotyczące ograniczeń systemu:</h4>
<ul>
<li>Front-end aplikacji będzie wykonany przy pomocy technologii HTML5, CSS3, JS.</li>
<li>Back-end systemu zostanie wykonany w języku programowania Java.</li>
<li>System nie będzie posiadał wsparcia technicznego, wszelka pomoc będzie dostępna w dokumentacji.</li>
<li>System będzie zoptymalizowany do stopnia zapewniającego relatywnie szybki czas reakcji przy stosunkowo niewielkim obciążeniu serwera.</li>
<li>System uruchomi się na serwerze obsługującym język programowania Java oraz system relacyjnej bazy danych.</li>
</ul>

<h4>Diagram encji</h4>
<img src="https://raw.githubusercontent.com/be1zi/LibraryAPI/master/documentation/diagramEncjii.png" alt="Diagram Encji">

<h4>Kolejne kroki utworzenia bazy przy użyciu replikacji:</h4>
1.Po restarcie serwera MySQL, logujemy się do niego (mysql -u root -p) i wydajemy następujące zapytanie sql : "mysql> SHOW MASTER STATUS\G". Powinno ono pokazać nam status serwera master.
2.Teraz należy stworzyć użytkownika, który będzie odpowiadał za autoryzację serwerów zapasowych. 
   W tym celu wydajemy zapytanie SQL: 
    CREATE USER 'repuser'@'%' IDENTIFIED BY 'haslo';
     GRANT REPLICATION SLAVE ON *.* TO 'repuser'@'%' IDENTIFIED BY 'haslo';
3.Następnym krokiem jest skonfigurowanie serwera slave. Najczęściej jest to plik /etc/my.cnf. 
  Powinny znaleźć się w nim takie opcje jak poniżej : 
    server-id=2
    master-host=192.168.0.164
    master-user=repuser
    master-password=haslo
    master-connect-retry=30 
4.Podłączamy się klientem do serwera i wydajemy polecenie, które załaduje pierwszą porcję danych z serwera głównego a następnie zatrzyma   replikację :
    mysql> LOAD DATA FROM MASTER;
    mysql> SLAVE STOP;
5.Kolejnym krokiem jest ustawienie parametrów replikacji. Wydajemy zapytanie SQL:
    CHANGE MASTER TO
    MASTER_HOST='192.168.0.164',
    MASTER_LOG_FILE='mysql-bin.000003',
    MASTER_LOG_POS=1274;
Wartości MASTER_LOG_FILE oraz MASTER_LOG_POS pobieramy z polecenia SHOW MASTER STATUS wydanego po stronie serwera głównego. 
6.Na koniec pozostaje nam włączyć replikację oraz sprawdzić jej status.
    mysql> START SLAVE;
    mysql> SHOW SLAVE STATUS\G;
