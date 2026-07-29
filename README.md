# 📦 Warehouse & Inventory Management System

RESTful API сервис для автоматизации управления складскими остатками, проведения накладных, учета производства продукции и формирования аналитических отчетов.

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?style=flat-square&logo=springboot)
![Spring JDBC](https://img.shields.io/badge/Database-Spring_JDBC-blue?style=flat-square)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue?style=flat-square&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=flat-square&logo=docker)

---

## 📌 Описание проекта

**Warehouse Management System** — это backend-приложение на **Spring Boot**, созданное для решения задач учета товаров на складах предприятия. Проект спроектирован с упором на высокую производительность за счет использования **Spring JDBC (`JdbcTemplate`)** и строгого контроля транзакций.

Архитектура построенная по стандартной многослойной схеме (`Controller` → `Service` → `Repository`), что делает код легко поддерживаемым и тестируемым.

---

## 🚀 Основные возможности

- **🏬 Управление складами и остатками:**
  - Отслеживание актуального количества товаров на складе (`StockRepository`).
  - Автоматическое создание записей при первом приходе товара и обновление существующего баланса.

- **🧾 Проведение накладных (Invoices):**
  - Поддержка операций: Продажа (`SALE`), Списание (`WRITE_OFF`).
  - Проверка наличия достаточного количества товара на складе перед списанием с выбросом `NotEnoughStockException`.

- **⚙️ Учет производства (Production):**
  - Выполнение операций производства в единой транзакции (`TransactionTemplate`).
  - Автоматическое увеличение баланса произведенной продукции.

- **📊 Аналитическая отчетность (Reports):**
  - Формирование отчетов по продажам (`SalesReportDto`) и списаниям (`WriteOffReportDto`) за произвольный период.
  - Автоматическая подстановка дат по умолчанию (с 1-го числа текущего месяца по сегодняшний день).

---

## 🛠 Технологический стек

- **Язык программирования:** Java 17
- **Фреймворк:** Spring Boot 3.x (Spring Web, Spring JDBC)
- **База данных:** PostgreSQL / H2 (для локального окружения)
- **Тестирование:** JUnit 5, Mockito, Spring Boot Test (`@SpringBootTest`, `@Transactional`)
- **Контейнеризация:** Docker, Docker Compose
- **Документация API:** OpenAPI 3.0 / Swagger UI

---

## ⚙️ Инструкция по сборке и запуску

### Предварительные требования
- Установленный **JDK 17** или выше
- Установленный **Docker** и **Docker Compose** (для запуска в контейнерах)

### 1. Клонирование репозитория
```bash
git clone https://github.com/uthbq/warehouse-api.git
cd warehouse-api
