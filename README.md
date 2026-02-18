# bonus-service
<h1>База данных</h1>
Для проекта была выбрана БД PostgreSQL.
В проекте присутствуют комментарии для понимания, где какие методы для чего используются.

<h1>Основные запросы к API</h1>

<h2>Users</h2>
POST:
/api/auth/register
/api/auth/login

GET:
/api/users - список всех пользователей
/api/users/{id} - ID пользователя карты

<h2>Cards</h2>
GET:
/api/cards - список всех карт
/api/cards/{cardNum}/get-balance - эндпоинт для получения баланса карты. В этом случае cardNum это номер карты
/api/cards/{cardNum}/history - история транзакций по карте. cardNum аналогична с get-balance

PATCH:
/api/cards/up-balance - начислить бонусы на определённую сумму. JSON выглядит вот так: {"cardNum":"123456","amount":100}, где cardNum - номер карты, а amount - сколько начислить бонусов
/api/cards/down-balance - снять бонусы на определённую сумму. JSON выглядит аналогично.
/api/cards/refund-bonus - вернуть бонусы в случае возврата товара. Работае аналогично up-balance, но срабатывает только при условии возврата товара
