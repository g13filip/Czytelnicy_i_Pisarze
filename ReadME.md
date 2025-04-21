# Problem Czytelników i Pisarzy w Javie

Projekt implementuje klasyczny problem **czytelników i pisarzy** z wykorzystaniem wątków oraz semaforów w języku Java.

## 📚 Opis problemu

W programowaniu współbieżnym problem czytelników i pisarzy dotyczy synchronizacji dostępu do wspólnego zasobu (np. pliku, bazy danych), gdzie:

- Wielu **czytelników** może jednocześnie odczytywać dane.
- **Pisarz** potrzebuje wyłącznego dostępu – tylko jeden pisarz może pisać i żaden czytelnik nie może wtedy czytać.

Ta implementacja **faworyzuje pisarzy**, tzn. gdy pisarz czeka, nowi czytelnicy nie są wpuszczani do biblioteki.

## 🛠 Struktura projektu

- `Main.java` – punkt startowy aplikacji. Tworzy i uruchamia wątki czytelników i pisarzy.
- `Library.java` – zarządza kolejką i stanem osób znajdujących się w bibliotece.
- `Resource.java` – odpowiada za koordynację dostępu do zasobu przy pomocy semaforów i flag atomowych.
- `Reader.java` – klasa reprezentująca wątek czytelnika.
- `Writer.java` – klasa reprezentująca wątek pisarza.
- `Man.java` – interfejs wspólny dla czytelników i pisarzy (do obsługi komunikatów).

## 🧵 Mechanizmy współbieżności

- `Semaphore` – ogranicza liczbę czytelników oraz zapewnia wyłączność dla pisarzy.
- `AtomicBoolean` – informuje, czy w bibliotece znajdują się czytelnicy.
- `synchronized` – zapewnia bezpieczny dostęp do współdzielonych struktur danych.

## ▶️ Uruchomienie

### Z linii poleceń

```bash
javac -d out $(find . -name "*.java")
java -cp out org.example.Main [liczbaCzytelników] [liczbaPisarzy]
```

Przykład:

```bash
java -cp out org.example.Main 10 3
```

Jeśli nie podano argumentów, domyślnie uruchomi się 10 czytelników i 3 pisarzy.

## 🧪 Przykładowy wynik działania

```
Reader 0 is waiting in queue
Reader 0 is reading
------------------------
1 readers waiting and 0 writers waiting
People in queue: [Reader 1]
People in library: [Reader 0]
------------------------
Writer 0 is waiting in queue
...
```

## 💡 Cechy

- **Priorytet dla pisarzy** – gdy jakiś pisarz czeka, nowi czytelnicy muszą poczekać.
- **Wielu czytelników** może czytać równocześnie.
- Czytelne komunikaty wypisywane w konsoli pokazują aktualny stan biblioteki.
- Zastosowano semafory, synchronizację oraz programowanie współbieżne.

## 📦 Pakiety

- `org.example` – logika główna.
- `org.example.people` – klasy reprezentujące osoby (czytelnicy i pisarze) oraz interfejs `Man`.

## 📌 Uwagi

- Czytelnicy czekają, jeśli pisarz znajduje się w kolejce.
- Pisarz blokuje innych pisarzy oraz czytelników na czas pisania.

