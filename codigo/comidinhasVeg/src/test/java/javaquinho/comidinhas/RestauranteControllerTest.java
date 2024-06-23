// package javaquinho.comidinhas;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestMethodOrder;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultActions;
// import javaquinho.comidinhas.models.Cliente;
// import javaquinho.comidinhas.models.Mesa;
// import javaquinho.comidinhas.models.Requisicao;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import java.util.Arrays;
// import java.util.List;

// @SpringBootTest
// @AutoConfigureMockMvc
// @TestMethodOrder(OrderAnnotation.class)
// public class RestauranteControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private ObjectMapper objectMapper;

//     private static Integer idClienteEduardo;
//     private static Long idRequisicao;

//     @Test
//     @Order(1)
//     public void testCriarClienteEduardo() throws Exception {
//         Cliente cliente = new Cliente();
//         cliente.setNome("Eduardo");
//         cliente.setTelefone("11884554323");
//         cliente.setCpf("12332315401");

//         String jsonCliente = objectMapper.writeValueAsString(cliente);

//         ResultActions resultActions = mockMvc.perform(post("/restaurante/cliente")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(jsonCliente));

//         resultActions.andExpect(status().isOk());

//         String content = resultActions.andReturn().getResponse().getContentAsString();
//         Cliente clienteCriado = objectMapper.readValue(content, Cliente.class);
//         idClienteEduardo = clienteCriado.getId();
//     }

//     @Test
//     @Order(2)
//     public void testCriarRequisicaoParaEduardo() throws Exception {
//         // Cria a requisição usando o cliente Eduardo
//         ResultActions resultActions = mockMvc.perform(post("/restaurante/" + idClienteEduardo + "/8"));

//         resultActions.andExpect(status().isOk());

//         // Obtém o ID da requisição criada
//         String content = resultActions.andReturn().getResponse().getContentAsString();
//         Requisicao requisicao = objectMapper.readValue(content, Requisicao.class);
//         idRequisicao = requisicao.getId();
//     }

//     @Test
//     @Order(3)
//     public void testAlocarMesaParaRequisicao() throws Exception {
//         // Alocar a mesa para a requisição criada
//         ResultActions resultActions = mockMvc.perform(put("/restaurante/alocar/" + idRequisicao));

//         resultActions.andExpect(status().isOk());
//     }

//     @Test
//     @Order(4)
//     public void testDesalocarMesaDeRequisicao() throws Exception {
//         // Desalocar a mesa da requisição
//         ResultActions resultActions = mockMvc.perform(put("/restaurante/desalocar/" + idRequisicao));

//         resultActions.andExpect(status().isOk());
//     }

//     @Test
//     public void testCriarClientesEmLote() throws Exception {
//         List<Cliente> clientes = Arrays.asList(
//                 new Cliente(null, "Samira", "11884557453", "32391315401"),
//                 new Cliente(null, "Leandra", "21880084323", "92312315401")
//         );

//         String jsonClientes = objectMapper.writeValueAsString(clientes);

//         ResultActions resultActions = mockMvc.perform(post("/restaurante/clientes")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(jsonClientes));

//         resultActions.andExpect(status().isOk());
//     }

//     @Test
//     public void testCriarMesa() throws Exception {
//         Mesa mesa = new Mesa();
//         mesa.setCapacidade(4);

//         String jsonMesa = objectMapper.writeValueAsString(mesa);

//         ResultActions resultActions = mockMvc.perform(post("/restaurante/mesa")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(jsonMesa));

//         resultActions.andExpect(status().isOk());
//     }

//     @Test
//     public void testCriarMesasEmLote() throws Exception {
//         List<Mesa> mesas = Arrays.asList(
//                 new Mesa(4),
//                 new Mesa(6),
//                 new Mesa(8)
//         );

//         String jsonMesas = objectMapper.writeValueAsString(mesas);

//         ResultActions resultActions = mockMvc.perform(post("/restaurante/mesas")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(jsonMesas));

//         resultActions.andExpect(status().isOk());
//     }

//     /*@Test
//     public void testCriarMenu() throws Exception {
//         Menu menu = new Menu();

//         String jsonMenu = objectMapper.writeValueAsString(menu);

//         ResultActions resultActions = mockMvc.perform(post("/restaurante/menus")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(jsonMenu));

//         resultActions.andExpect(status().isOk());
//     }*/
// }
