
<div align="center">

# 🌊 Blueguard 🌊

<img src="https://raw.githubusercontent.com/bruno1098/BlueGuard/main/assets/images/logo.png" alt="Blueguard" width="150"/>

</div>

<div align="center">

## Visão Geral
</div>

**Blueguard** é uma aplicação Java robusta desenvolvida com **Spring Boot**, **Lombok** e **HATEOAS**. O projeto oferece um conjunto de APIs RESTful para gerenciar áreas marinhas, comunidades, marcações, observações e qualidade da água. Ele é projetado para ser escalável, fácil de manter e altamente eficiente.

<div align="center">

## 🚀 Tecnologias Utilizadas

</div>


- **Java 19** <img src="https://cdn-icons-png.flaticon.com/128/226/226777.png" alt="Java" width="40" height="40" style="border-radius: 50px;" align="center">
- **Spring Boot** <img src="https://img.icons8.com/?size=96&id=90519&format=png" alt="Java" width="40" height="40" style="border-radius: 50px;" align="center">
- **Lombok** <img src="https://avatars.githubusercontent.com/u/45949248?s=200&v=4" alt="Java" width="40" height="40" style="border-radius: 50px;" align="center">
- **HATEOAS** <img src="https://springhow.com/wp-content/uploads/2021/02/spring-hateoas.png" alt="Java" width="40" height="40" style="border-radius: 50px;" align="center">
- **Docker** <img src="https://camo.githubusercontent.com/fab6b1b1c4794896090da7b2b336caee330883aa9398b95fd81984fd7003ae93/68747470733a2f2f73746f726167652e676f6f676c65617069732e636f6d2f646f776e6c6f61642f73746f726167652f76312f622f6d61636f732d69636f6e732e61707073706f742e636f6d2f6f2f69636f6e735f617070726f766564253246706e67253246313630353336343933323737385f446f636b65725f416c745f322e706e673f67656e65726174696f6e3d3136303533373936303030373430383026616c743d6d65646961" alt="Java" width="40" height="40" style="border-radius: 50px;" align="center">

<h2 align="center">🎥 Demonstração do Aplicativo 🎥</h2>
<p align="center">
  <a href="https://www.youtube.com/watch?v=zJTUvL5StFQ" target="_blank">
    <img src="https://img.youtube.com/vi/Wrm3gmGxVZU/maxresdefault.jpg" alt="Demonstração do Aplicativo" style="width:80%; border: 1px solid #ddd; border-radius: 4px; padding: 5px;"/>
  </a>
</p>
  <p style="text-align: justify;">
    Assista ao vídeo para ver o BlueGuard em ação! No vídeo, demonstramos como é fácil registrar um novo local, visualizar informações detalhadas no mapa e outras funcionalidades.
  </p>
</div>


<div align="center">

## 📑 Endpoints e Controladores (Clique na seta para visualizar)

</div>

<details>
  <summary><strong>AreaMarinhaController</strong></summary>
  <ul>
    <li><strong>Base Endpoint:</strong> <code>/area</code></li>
    <li><strong>Métodos HTTP:</strong></li>
    <ul>
      <li><code>GET /area</code>: Listar todas as áreas marinhas.</li>
      <li><code>POST /area</code>: Criar uma nova área marinha.</li>
      <li><code>PUT /area/{id}</code>: Atualizar uma área marinha existente.</li>
      <li><code>GET /area/{id}</code>: Consultar uma área marinha por ID.</li>
      <li><code>DELETE /area/{id}</code>: Deletar uma área marinha por ID.</li>
      <li><code>PATCH /area/{id}</code>: Atualizar parcialmente uma área marinha.</li>
      <li><code>HEAD /area/{id}</code>: Verificar a existência de uma área marinha por ID.</li>
    </ul>
  </ul>
</details>

<details>
  <summary><strong>ComunidadeController</strong></summary>
  <ul>
    <li><strong>Base Endpoint:</strong> <code>/comunidade</code></li>
    <li><strong>Métodos HTTP:</strong></li>
    <ul>
      <li><code>GET /comunidade</code>: Listar todos os posts.</li>
      <li><code>POST /comunidade</code>: Criar um novo post na comunidade.</li>
      <li><code>PUT /comunidade/{id}</code>: Atualizar um post existente.</li>
      <li><code>GET /comunidade/{id}</code>: Consultar um post por ID.</li>
      <li><code>DELETE /comunidade/{id}</code>: Deletar um post por ID.</li>
      <li><code>PATCH /comunidade/{id}</code>: Atualizar parcialmente um post.</li>
      <li><code>HEAD /comunidade/{id}</code>: Verificar a existência de um post por ID.</li>
    </ul>
  </ul>
</details>

<details>
  <summary><strong>MarcacoesController</strong></summary>
  <ul>
    <li><strong>Base Endpoint:</strong> <code>/marcacoes</code></li>
    <li><strong>Métodos HTTP:</strong></li>
    <ul>
      <li><code>GET /marcacoes</code>: Listar todas as marcações.</li>
      <li><code>POST /marcacoes</code>: Criar uma nova marcação.</li>
      <li><code>PUT /marcacoes/{id}</code>: Atualizar uma marcação existente.</li>
      <li><code>GET /marcacoes/{id}</code>: Consultar uma marcação por ID.</li>
      <li><code>DELETE /marcacoes/{id}</code>: Deletar uma marcação por ID.</li>
      <li><code>PATCH /marcacoes/{id}</code>: Atualizar parcialmente uma marcação.</li>
      <li><code>HEAD /marcacoes/{id}</code>: Verificar a existência de uma marcação por ID.</li>
    </ul>
  </ul>
</details>

<details>
  <summary><strong>ObservacaoController</strong></summary>
  <ul>
    <li><strong>Base Endpoint:</strong> <code>/observacao</code></li>
    <li><strong>Métodos HTTP:</strong></li>
    <ul>
      <li><code>GET /observacao</code>: Listar todas as observações.</li>
      <li><code>POST /observacao</code>: Criar uma nova observação.</li>
      <li><code>PUT /observacao/{id}</code>: Atualizar uma observação existente.</li>
      <li><code>GET /observacao/{id}</code>: Consultar uma observação por ID.</li>
      <li><code>DELETE /observacao/{id}</code>: Deletar uma observação por ID.</li>
      <li><code>PATCH /observacao/{id}</code>: Atualizar parcialmente uma observação.</li>
      <li><code>HEAD /observacao/{id}</code>: Verificar a existência de uma observação por ID.</li>
    </ul>
  </ul>
</details>

<details>
  <summary><strong>QualidadeAguaController</strong></summary>
  <ul>
    <li><strong>Base Endpoint:</strong> <code>/qualidade</code></li>
    <li><strong>Métodos HTTP:</strong></li>
    <ul>
      <li><code>GET /qualidade</code>: Listar todas as qualidades da água.</li>
      <li><code>POST /qualidade</code>: Criar uma nova qualidade da água.</li>
      <li><code>PUT /qualidade/{id}</code>: Atualizar uma qualidade da água existente.</li>
      <li><code>GET /qualidade/{id}</code>: Consultar uma qualidade da água por ID.</li>
      <li><code>DELETE /qualidade/{id}</code>: Deletar uma qualidade da água por ID.</li>
      <li><code>PATCH /qualidade/{id}</code>: Atualizar parcialmente uma qualidade da água.</li>
      <li><code>HEAD /qualidade/{id}</code>: Verificar a existência de uma qualidade da água por ID.</li>
    </ul>
  </ul>
</details>

<div align="center">

## 🔧 Recursos Utilizados

</div>

### Lombok

Lombok é utilizado para reduzir a verbosidade do código, gerando automaticamente getters, setters, construtores e outros métodos comuns. As anotações do Lombok utilizadas incluem:

- **@Data**
- **@Getter**
- **@Setter**
- **@AllArgsConstructor**
- **@NoArgsConstructor**
- **@Builder**

### HATEOAS

O projeto incorpora HATEOAS para facilitar a navegação entre recursos, adicionando links aos DTOs retornados pela API:

- **EntityModel**: Envolve DTOs com links HATEOAS.
- **WebMvcLinkBuilder**: Constrói links com base nos métodos dos controladores.

<div align="center">
  
## 📚 Documentação da API

</div>

A documentação da API foi realizada utilizando o Swagger. Para acessar a documentação interativa e explorar os endpoints disponíveis, acesse o seguinte link:

Swagger UI `http://localhost:8080/swagger-ui/index.html` 

![Swagger](https://img.shields.io/badge/documentation-Swagger-brightgreen)



<div align="center">

## 🌟 Funcionalidades Principais

</div>

1. **Gerenciamento de Áreas Marinhas**: CRUD para áreas marinhas.
2. **Gerenciamento de Comunidades**: CRUD para posts na comunidade.
3. **Gerenciamento de Marcações**: CRUD para marcações de locais de monitoramento.
4. **Gerenciamento de Observações**: CRUD para observações em diferentes locais.
5. **Gerenciamento da Qualidade da Água**: CRUD para informações de qualidade da água.

\
<div align="center">

## 🛠️ Como Configurar e Executar

</div>

### Pré-requisitos

- Java 19 ou superior
- Maven

### Compilação e Execução

Para compilar e executar o projeto, siga os passos abaixo:

1. **Clonar o repositório**:
   ```sh
   git clone https://github.com/rafak7/GlobalSolution-JAVA
   cd blueguard
   ```

2. **Compilar o projeto**:
   ```sh
   ./mvnw clean install
   ```

3. **Executar o projeto**:
   ```sh
   ./mvnw spring-boot:run
   ```
   
<div align="center">

### Docker 🐳

</div>

O projeto inclui um Dockerfile para facilitar a criação de um container Docker da aplicação.

#### Construir a imagem Docker

```sh
docker build -t blueguard .
```

#### Executar o container Docker

```sh
docker run -p 8080:8080 blueguard
```


