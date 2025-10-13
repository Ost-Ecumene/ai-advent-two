const express = require('express');
const { spawn } = require('child_process');
const cors = require('cors');

const app = express();
const PORT = 3000;

app.use(cors());
app.use(express.json());

let mcpProcess = null;
let mcpTools = [];
let pendingRequests = new Map();
let requestId = 0;

// Запуск MCP сервера
function startMCPServer() {
    console.log('Запуск MCP сервера...');
    
    mcpProcess = spawn('npx', ['-y', '@modelcontextprotocol/server-filesystem'], {
        stdio: ['pipe', 'pipe', 'pipe']
    });

    mcpProcess.on('error', (error) => {
        console.error('Ошибка запуска MCP сервера:', error);
    });

    mcpProcess.on('exit', (code) => {
        console.log(`MCP сервер завершился с кодом ${code}`);
    });

    // Обработка сообщений от MCP сервера
    mcpProcess.stdout.on('data', (data) => {
        try {
            const message = JSON.parse(data.toString());
            console.log('Получено сообщение от MCP:', message);
            
            if (message.id && pendingRequests.has(message.id)) {
                const { resolve, reject } = pendingRequests.get(message.id);
                pendingRequests.delete(message.id);
                
                if (message.error) {
                    reject(new Error(message.error.message || 'MCP ошибка'));
                } else {
                    resolve(message.result);
                }
            }
            
            if (message.method === 'tools/list' && message.result) {
                mcpTools = message.result.tools || [];
                console.log('Загружены инструменты:', mcpTools.length);
            }
        } catch (e) {
            console.error('Ошибка парсинга MCP сообщения:', e);
        }
    });

    mcpProcess.stderr.on('data', (data) => {
        console.error('MCP stderr:', data.toString());
    });

    // Инициализация MCP сервера
    setTimeout(() => {
        initializeMCPServer();
    }, 2000);
}

// Инициализация MCP сервера
function initializeMCPServer() {
    console.log('Инициализация MCP сервера...');
    
    // Отправляем запрос на получение списка инструментов
    const listToolsRequest = {
        jsonrpc: "2.0",
        id: ++requestId,
        method: "tools/list"
    };
    
    sendMCPRequest(listToolsRequest);
}

// Отправка запроса к MCP серверу
function sendMCPRequest(request) {
    return new Promise((resolve, reject) => {
        if (!mcpProcess) {
            reject(new Error('MCP сервер не запущен'));
            return;
        }
        
        pendingRequests.set(request.id, { resolve, reject });
        
        try {
            mcpProcess.stdin.write(JSON.stringify(request) + '\n');
        } catch (error) {
            pendingRequests.delete(request.id);
            reject(error);
        }
    });
}

// API endpoints
app.get('/api/tools', (req, res) => {
    console.log('Запрос списка инструментов');
    res.json({ tools: mcpTools });
});

app.post('/api/tools/execute', async (req, res) => {
    const { toolName, arguments } = req.body;
    
    console.log(`Выполнение инструмента: ${toolName}`, arguments);
    
    try {
        const executeRequest = {
            jsonrpc: "2.0",
            id: ++requestId,
            method: "tools/call",
            params: {
                name: toolName,
                arguments: arguments || {}
            }
        };
        
        const result = await sendMCPRequest(executeRequest);
        
        res.json({ 
            status: 'success', 
            result: result,
            tool: toolName 
        });
    } catch (error) {
        console.error('Ошибка выполнения инструмента:', error);
        res.status(500).json({ 
            status: 'error', 
            error: error.message,
            tool: toolName 
        });
    }
});

// Health check endpoint
app.get('/api/health', (req, res) => {
    res.json({ 
        status: 'ok', 
        mcpConnected: mcpProcess !== null,
        toolsCount: mcpTools.length 
    });
});

// Запуск сервера
app.listen(PORT, () => {
    console.log(`MCP Bridge Server запущен на порту ${PORT}`);
    console.log(`Доступен по адресу: http://localhost:${PORT}`);
    startMCPServer();
});

// Graceful shutdown
process.on('SIGINT', () => {
    console.log('Завершение работы сервера...');
    if (mcpProcess) {
        mcpProcess.kill();
    }
    process.exit(0);
});




