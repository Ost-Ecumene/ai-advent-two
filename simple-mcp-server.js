const express = require('express');
const cors = require('cors');

const app = express();
const PORT = 3000;

app.use(cors());
app.use(express.json());

const mockTools = [
  {
    name: "read_file",
    description: "Читает содержимое файла",
    inputSchema: {
      type: "object",
      properties: {
        path: { type: "string", description: "Путь к файлу" }
      },
      required: ["path"]
    }
  },
  {
    name: "write_file",
    description: "Записывает содержимое в файл",
    inputSchema: {
      type: "object",
      properties: {
        path: { type: "string", description: "Путь к файлу" },
        content: { type: "string", description: "Содержимое файла" }
      },
      required: ["path", "content"]
    }
  },
  {
    name: "list_directory",
    description: "Список файлов в директории",
    inputSchema: {
      type: "object",
      properties: {
        path: { type: "string", description: "Путь к директории" }
      },
      required: ["path"]
    }
  }
];

app.get('/api/health', (req, res) => {
  res.json({ status: 'ok', mcpConnected: true, toolsCount: mockTools.length });
});

app.get('/api/tools', (req, res) => {
  res.json({ tools: mockTools });
});

app.post('/api/tools/execute', (req, res) => {
  const { toolName, arguments: args } = req.body || {};
  let result;
  switch (toolName) {
    case 'read_file':
      result = `Содержимое файла ${args?.path ?? 'unknown'}: "Это тестовое содержимое файла"`;
      break;
    case 'write_file':
      result = `Файл ${args?.path ?? 'unknown'} успешно записан.`;
      break;
    case 'list_directory':
      result = `Содержимое директории ${args?.path ?? 'unknown'}: file1.txt, file2.txt, folder1/`;
      break;
    default:
      result = `Инструмент ${toolName} выполнен (демо).`;
  }
  res.json({ status: 'success', result, tool: toolName });
});

app.listen(PORT, () => {
  console.log(`MCP Bridge Server запущен на порту ${PORT}`);
  console.log(`Доступен по адресу: http://localhost:${PORT}`);
  console.log('Используется упрощенная версия с моковыми данными');
  console.log(`Доступно инструментов: ${mockTools.length}`);
});





