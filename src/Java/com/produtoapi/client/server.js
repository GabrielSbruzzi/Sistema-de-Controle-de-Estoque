const express = require("express");
const cors = require("cors");
const app = express();
const PORT = 8080;

// Middleware
app.use(cors()); // Permite requisições do front-end
app.use(express.json());
app.use(express.static("public")); // Serve arquivos do front-end

// "Banco de dados" em memória
let movimentacoes = [];
let nextId = 1;

// GET - Listar movimentações
app.get("/movimentacoes", (req, res) => {
    res.json(movimentacoes);
});

// POST - Registrar movimentação
app.post("/movimentacoes", (req, res) => {
    const { produtoId, tipo, quantidade } = req.body;

    if (!produtoId || !tipo || !quantidade) {
        return res.status(400).json({ message: "Todos os campos são obrigatórios" });
    }

    if (quantidade <= 0) {
        return res.status(400).json({ message: "Quantidade deve ser maior que zero" });
    }

    const novaMov = { id: nextId++, produtoId, tipo, quantidade, data: new Date() };
    movimentacoes.push(novaMov);

    res.status(201).json(novaMov);
});

app.listen(PORT, () => {
    console.log(`Servidor rodando em http://localhost:${PORT}`);
});
