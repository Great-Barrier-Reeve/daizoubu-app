# daizoubu

> **Takeaway? That's okay!**

daizoubu is an Android app that connects **buyers** who want food  delivered with **runners** who earn a tip (bounty) by fulfilling those requests.

## Features

- **Magic-link authentication** — passwordless login via Firebase email link
- **Place orders** — browse stores, customise menu items, manage a cart, and check out with a tip
- **Browse bounties** — view all open delivery requests and accept one to earn the bounty
- **Errand status tracking** — live status from `REQUESTED` → `ACCEPTED` → `COLLECTED` → `DELIVERED`
- **Delivery location selector** — pick from a curated list of drop-off points
- **Light & dark mode** — full Material Design 3 theming

## Screenshots

*Screenshots coming soon.*

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Min SDK | 28 (Android 9) |
| Target SDK | 36 |
| UI | Material Design 3, AndroidX, ConstraintLayout |
| Networking | Retrofit 2 + OkHttp3 + Gson |
| Auth | Firebase Authentication (email link) |
| Architecture | Repository + ViewModel + LiveData |
| Build | Gradle 9.3, AGP 9.1, Version Catalog |
| License | GNU GPL v3 |

## Architecture

```
┌─────────────────────────────────┐
│           UI Layer              │  Activities + ViewModels (LiveData)
├─────────────────────────────────┤
│        Repository Layer         │  AuthRepository, ErrandRepository,
│                                 │  CartRepository, LocationRepository, …
├─────────────────────────────────┤
│          API Layer              │  Retrofit services (AuthService,
│                                 │  ErrandService) via RetrofitClient
└─────────────────────────────────┘
```

The `RetrofitClient` singleton automatically attaches the Firebase JWT token to every request via an OkHttp interceptor. Cart and session state are held in singleton repositories backed by `SharedPreferences`.

## Project Structure

```
app/src/main/java/org/greatbarrierreeve/daizoubu/
├── api/                   # Retrofit service interfaces
│   ├── AuthService.java
│   └── ErrandService.java
├── network/
│   └── RetrofitClient.java
├── data/
│   ├── model/             # POJOs: Errand, Store, MenuItem, OrderItem, …
│   └── repository/        # AuthRepository, ErrandRepository, CartRepository, …
├── ui/
│   ├── auth/              # LoginActivity
│   ├── homepage/          # MainActivity (dashboard)
│   ├── bounties/          # BountiesActivity, BountyActivity, UserBountyActivity
│   ├── order/             # OrderActivity, MenuActivity, ItemDetailActivity
│   │   ├── cart/          # CartActivity, CartViewModel
│   │   └── location/      # LocationActivity, LocationViewModel
│   └── common/            # Shared UI utilities
└── DaizoubuApplication.java
```

## Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- JDK 17
- Android SDK with API level 28+
- A Firebase project with **Email Link (passwordless)** authentication enabled
- The daizoubu backend running locally (Spring Boot on port 8080)

### 1 — Clone the repo

```bash
git clone https://github.com/Great-Barrier-Reeve/daizoubu-app.git
cd daizoubu-app
```

### 2 — Add Firebase config

Download `google-services.json` from your Firebase project console and place it at:

```
app/google-services.json
```

### 3 — Configure the backend URL

The app defaults to `http://10.0.2.2:8080` (the Android emulator's alias for localhost). If you are running on a physical device or a different host, update the base URL in:

[RetrofitClient.java](app/src/main/java/org/greatbarrierreeve/daizoubu/network/RetrofitClient.java)

### 4 — Build & run

Open the project in Android Studio and click **Run**, or build from the command line:

```bash
./gradlew assembleDebug
```

---

## API Overview

All requests are sent to the configured base URL with an `Authorization: Bearer <token>` header injected automatically.

### Auth

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/authenticate/send-link?email={email}` | Send magic login link |
| `POST` | `/api/user/verification` | Verify Firebase token with backend |
| `GET` | `/api/user/{id}` | Fetch user profile |

### Errands

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/errands/available` | List all open bounties |
| `GET` | `/api/errands/{id}` | Get errand detail |
| `GET` | `/api/errands/buyer/{buyerId}` | Orders placed by user |
| `GET` | `/api/errands/runner/{runnerId}` | Runs accepted by user |
| `POST` | `/api/errands` | Create a new errand/bounty |
| `POST` | `/api/errands/{id}/accept` | Accept a bounty |
| `POST` | `/api/errands/{id}/transition` | Update errand status |
| `GET` | `/api/errands/stores/available` | List stores |
| `GET` | `/api/errands/menu/{storeId}` | Get store menu |
| `GET` | `/api/errands/locations` | Get delivery locations |

---

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feat/your-feature`
3. Commit your changes with a descriptive message
4. Open a pull request against `main`

Please keep PRs focused on a single concern and ensure the app builds without warnings before submitting.

## License

This project is licensed under the **GNU General Public License v3.0**. See [LICENSE](LICENSE) for details.
