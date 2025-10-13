const http = require('http');

const testMCPServer = () => {
    console.log('Тестирование MCP Bridge Server...');
    
    // Тест health endpoint
    const healthOptions = {
        hostname: 'localhost',
        port: 3000,
        path: '/api/health',
        method: 'GET'
    };
    
    const healthReq = http.request(healthOptions, (res) => {
        console.log(`Health check status: ${res.statusCode}`);
        
        let data = '';
        res.on('data', (chunk) => {
            data += chunk;
        });
        
        res.on('end', () => {
            console.log('Health response:', JSON.parse(data));
            
            // Тест tools endpoint
            const toolsOptions = {
                hostname: 'localhost',
                port: 3000,
                path: '/api/tools',
                method: 'GET'
            };
            
            const toolsReq = http.request(toolsOptions, (toolsRes) => {
                console.log(`Tools endpoint status: ${toolsRes.statusCode}`);
                
                let toolsData = '';
                toolsRes.on('data', (chunk) => {
                    toolsData += chunk;
                });
                
                toolsRes.on('end', () => {
                    console.log('Tools response:', JSON.parse(toolsData));
                    console.log('Тестирование завершено!');
                });
            });
            
            toolsReq.on('error', (err) => {
                console.error('Ошибка при запросе tools:', err);
            });
            
            toolsReq.end();
        });
    });
    
    healthReq.on('error', (err) => {
        console.error('Ошибка при health check:', err);
        console.log('Убедитесь, что MCP Bridge Server запущен на порту 3000');
    });
    
    healthReq.end();
};

// Запуск теста через 3 секунды (время на запуск сервера)
setTimeout(testMCPServer, 3000);




