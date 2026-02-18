# bonus-service
<h1>База данных</h1><br/>
Для проекта была выбрана БД PostgreSQL.<br/>
В проекте присутствуют комментарии для понимания, где какие методы для чего используются.<br/>

<h1>Example (пошаговая инструкция)</h1>
1. POST /api/auth/register, JSON - {"username":"yourUser","password":"yourPassword"}<br/>
2. POST /api/auth/login, JSON - {"username":"yourUser","password":"yourPassword"}<br/>
3. Любой запрос*, написанный ниже.<br/>
*В зависимости от роли пользователя (которую можно сменить только в БД, т.к. не было прописано в ТЗ о предусмотрении функции редактирования роли) можно выполнить либо только GET запросы, либо PATCH, POST и GET запросы соответственно.<br/>
<br/>
<h1>Основные запросы к API</h1><br/>
<br/>
<h2>Users</h2><br/>
POST:<br/>
/api/auth/register - регистрация. JSON выглядит следующим образом: {"usernane":"anyUser","password":"anyPassword"}. Пароли хэшируются для безопасности.<br/>
/api/auth/login - вход. JSON аналогичный.<br/>
<br/>
GET:<br/>
/api/users - список всех пользователей.<br/>
/api/users/{id} - ID пользователя карты.<br/>
<br/>
<h2>Cards</h2><br/>
GET:<br/>
/api/cards - список всех карт.<br/>
/api/cards/{cardNum}/get-balance - эндпоинт для получения баланса карты. В этом случае cardNum это номер карты.<br/>
/api/cards/{cardNum}/history - история транзакций по карте. cardNum аналогична с get-balance.<br/>
<br/>
PATCH:<br/>
/api/cards/up-balance - начислить бонусы на определённую сумму. JSON выглядит вот так: {"cardNum":"123456","amount":100}, где cardNum - номер карты, а amount - сколько начислить бонусов<br/>
/api/cards/down-balance - снять бонусы на определённую сумму. JSON выглядит аналогично.<br/>
/api/cards/refund-bonus - вернуть бонусы в случае возврата товара. Работае аналогично up-balance, но срабатывает только при условии возврата товара.<br/>
<br/>
<h1>Миграция БД</h1>
<br/>
Миграция происходит с помощью Flyway. Файлы для миграции находятся по пути resources/db/migration.
