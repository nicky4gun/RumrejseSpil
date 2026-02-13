
# Hvordan programmet køres?
- Du indtaster et navn som din captain skal hedde
- Her efter skal du navnet på dit skib og så er du klar til at flyve 
- Du får så random events som sker på din rejse og du skal vælge hvad du vil gøre i de forskellige situationer
- Du kan se din status ændre sig for hver event og du skal prøve at holde din status så høj som muligt for at kunne overleve hele rejsen
- Du kan også vælge at købe ting i en shop for at forbedre din status og gøre det lettere for dig selv at overleve hele rejsen.

# Hvilke menu punkter der findes?
Hvert event er random og du kan vælge mellem op til 3 forskellige ting at gøre i hvert event.

# Hvilke exception du har lavet og hvor de kastes?
- Vi har lavet en exception der hedder "IllegalArgumentException" som kastes når man indtaster et navn/skibs navn som er blank
- En exception der hedder CriticalStatusException som når ens status går under 0 eller under 10 ville den skive game over og kaste denne exception
- En exception der hedder InvalidTradeException som kastes når man ikke har nok penge til at købe noget i shoppen
- En exception der hedder NumberFormatException som kastes når spilleren indtaster noget andet end et tal under events 

# En kort testliste med 6 til 10 punkter over hvad du selv har prøvet
1. Vi har prøvet at indtaste et blankt navn og den fortæller at man skal indtaste et navn  
2. Vi har prøvet at indtaste et blankt skibs navn og den fortæller at man skal indtaste et skibs navn
3. Vi har prøvet at indtaste en valmulighed som ikke er et tal og den fortæller at man skal indtaste et tal
4. Vi har prøvet at indtaste et tal som ikke er en valmulighed og den fortæller at man skal indtaste en gyldig valmulighed
5. Vi har prøvet at købe fuel i shoppen uden at have nok penge og den fortæller at man skal have nok penge for at købe det
6. Vi har prøvet at købe fuel og bruge -1 eller 0 og den smider dig til bage til valgmulighederne
7. Man kan kun bruge 1 repair kit og hvis du prøver at bruge det (hvis brugt), bliver man bedt om tag option 2 i stedet. Også hvis integrity er 100, får brugeren af besked om at repair kit ingen effekt har.
8. Vi har prøvet at få vores status under 0 og den fortæller at man er død og kaster en CriticalStatusException
9. Vi har prøvet at få vores status under 10 og den fortæller at man er i kritisk tilstand og kaster en CriticalStatusException
10. Vi har prøvet at få fuel under 0 og den fortæller game over og kaster enCriticalStatusException som fortæller at man er løbet tør for fuel og er strandet i rummet (svært er løbet tør for fuel)

# Vi har lavet nogen ting ud over hvad der blev bedt om i opgaven
- Vi har lavet flere events end de 3 der blev bedt om i opgaven
- Vi har lavet ændringer på fuel mængden der bliver brugt i de forskellige events 
- Vi har lavet ændringer på hvor meget integritet der bliver tabt i de forskellige events
- Vi har lavet en random generator til at bestemmer events der skal kommer frem, den stopper også efter 7 events 
