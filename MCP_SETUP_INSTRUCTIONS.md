# MCP (Model Context Protocol) Интеграция

## Обзор

Этот проект интегрирует MCP (Model Context Protocol) в Android приложение AI Advent 2. MCP позволяет подключаться к различным серверам и использовать их инструменты.

## Архитектура

- **Android приложение** - использует HTTP API для связи с MCP bridge сервером
- **MCP Bridge Server** - Node.js сервер, который работает как мост между Android и MCP серверами
- **MCP Server** - фактический MCP сервер (например, filesystem server)

## Установка и запуск

### 1. Установка Node.js

Убедитесь, что у вас установлен Node.js версии 16 или выше:

```bash
node --version
npm --version
```

### 2. Установка зависимостей

```bash
npm install
```

### 3. Запуск MCP Bridge Server

```bash
npm start
```

Сервер будет доступен по адресу: `http://localhost:3000`

### 4. Запуск Android приложения

1. Откройте проект в Android Studio
2. Синхронизируйте Gradle
3. Запустите приложение на эмуляторе или устройстве

## API Endpoints

### GET /api/tools
Получить список доступных MCP инструментов

**Ответ:**
```json
{
  "tools": [
    {
      "name": "read_file",
      "description": "Читает содержимое файла",
      "inputSchema": {
        "type": "object",
        "properties": {
          "path": {
            "type": "string",
            "description": "Путь к файлу"
          }
        },
        "required": ["path"]
      }
    }
  ]
}
```

### POST /api/tools/execute
Выполнить MCP инструмент

**Запрос:**
```json
{
  "toolName": "read_file",
  "arguments": {
    "path": "/path/to/file.txt"
  }
}
```

**Ответ:**
```json
{
  "status": "success",
  "result": "содержимое файла",
  "tool": "read_file"
}
```

### GET /api/health
Проверить состояние сервера

**Ответ:**
```json
{
  "status": "ok",
  "mcpConnected": true,
  "toolsCount": 5
}
```

## Структура проекта

```
├── core/mcp/                    # MCP core модуль
│   ├── api/                     # HTTP API клиент
│   ├── repository/              # Репозиторий для работы с MCP
│   └── di/                      # Dependency Injection
├── feature/mcp/                 # MCP UI feature
│   ├── ui/                      # UI экраны
│   └── di/                      # DI для feature
├── mcp-bridge-server.js         # Node.js bridge сервер
├── package.json                 # Node.js зависимости
└── MCP_SETUP_INSTRUCTIONS.md    # Эта инструкция
```

## Настройка для разработки

### Изменение URL сервера

По умолчанию Android приложение подключается к `http://10.0.2.2:3000` (localhost эмулятора).

Для изменения URL отредактируйте файл `core/mcp/src/main/java/com/povush/mcp/api/MCPClient.kt`:

```kotlin
private val baseUrl = "http://YOUR_IP:3000" // Замените на ваш IP
```

### Логирование

Для включения детального логирования HTTP запросов, логи уже включены в `MCPClient.kt`.

## Отладка

### Проверка состояния сервера

```bash
curl http://localhost:3000/api/health
```

### Проверка списка инструментов

```bash
curl http://localhost:3000/api/tools
```

### Проверка логов

MCP Bridge Server выводит подробные логи в консоль. Следите за сообщениями о:
- Запуске MCP сервера
- Загрузке инструментов
- HTTP запросах от Android приложения

## Возможные проблемы

### 1. MCP сервер не запускается

- Убедитесь, что у вас установлен Node.js
- Проверьте, что пакет `@modelcontextprotocol/server-filesystem` установлен
- Проверьте логи в консоли

### 2. Android приложение не может подключиться

- Убедитесь, что MCP Bridge Server запущен
- Проверьте, что эмулятор использует правильный IP (10.0.2.2)
- Проверьте логи Android приложения

### 3. Инструменты не загружаются

- Проверьте, что MCP сервер успешно запустился
- Проверьте логи MCP Bridge Server
- Попробуйте перезапустить сервер

## Расширение функциональности

### Добавление новых MCP серверов

1. Измените команду запуска в `mcp-bridge-server.js`:
```javascript
mcpProcess = spawn('npx', ['-y', '@modelcontextprotocol/server-github'], {
    stdio: ['pipe', 'pipe', 'pipe']
});
```

2. Перезапустите сервер

### Добавление новых API endpoints

Добавьте новые endpoints в `mcp-bridge-server.js`:

```javascript
app.get('/api/custom', (req, res) => {
    // Ваша логика
    res.json({ result: 'success' });
});
```

## Лицензия

MIT License




