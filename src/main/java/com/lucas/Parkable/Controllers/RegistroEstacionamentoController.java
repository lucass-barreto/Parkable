package com.lucas.Parkable.Controllers;

import com.lucas.Parkable.DTOs.RegistroEstacionamento.RegistroEstacionamentoRequestDTO;
import com.lucas.Parkable.DTOs.RegistroEstacionamento.RegistroEstacionamentoResponseDTO;
import com.lucas.Parkable.Enums.TipoVeiculo;
import com.lucas.Parkable.Service.RegistroEstacionamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Gerenciamento dos veículos do estacionamento", description = "Controller da API para gerenciamento de veículos no estacionamento")
@RestController
@RequestMapping("/estacionamento")
public class RegistroEstacionamentoController {

    @Autowired
    private RegistroEstacionamentoService registroEstacionamentoService;

    @Operation(summary = "Registrar entrada de veículo", description = "Registra no sistema a entrada de um veículo com suas respectivas informações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entrada registrada com sucesso em sua respectiva vaga"),
            @ApiResponse(responseCode = "400", description = "Erro ao adicionar o veículo.")
    })
    @PostMapping("/registrarEntrada")
    public ResponseEntity<RegistroEstacionamentoResponseDTO> registarEntrada(@Parameter(description = "Informações do veículo e da vaga respectiva.") @RequestBody RegistroEstacionamentoRequestDTO registroEstacionamentoRequestDTO){
        RegistroEstacionamentoResponseDTO veiculoAdicionado = registroEstacionamentoService.registrarEntrada(registroEstacionamentoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(veiculoAdicionado);
    }

    @Operation(summary = "Registra a saída de um veículo", description = "Registra a saída de um veículo, libera a vaga e retorna o valor a pagar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saída do veículo registrada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado no estacionamento.")
    })
    @PutMapping("/registrarSaida/{id}")
    public ResponseEntity<?> registrarSaida (@Parameter(name = "ID do veículo respectivo para registrar a saída.")@PathVariable Long id){
        RegistroEstacionamentoResponseDTO registroComSaida = registroEstacionamentoService.registrarSaida(id);

        if (registroComSaida != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(registroComSaida);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não foi encontrado nenhum veículo com ID #" + id + ".");
        }
    }

    @Operation(summary = "Dashboard dos veículos do estacionamento.", description = "Exibe todas as informações de todos os veículos da database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dashboard exibido com todos os veículos"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    })
    @GetMapping("/dashboard")
    public ResponseEntity<List<RegistroEstacionamentoResponseDTO>> exibirRegistros(){
        List<RegistroEstacionamentoResponseDTO> registrosEncontrados = registroEstacionamentoService.exibirRegistros();
        return ResponseEntity.ok(registrosEncontrados);
    }

    @Operation(summary = "Exibe os veículos do banco de acordo com o seu tipo.", description = "Exibe todos os registro de um determinado tipo de veículo solicitado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veículos encontrados com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    })
    @GetMapping("/tipo/{tipoVeiculo}")
    public ResponseEntity<List<RegistroEstacionamentoResponseDTO>> exibirRegistrosTipo(@Parameter(description = "Cliente informa qual tipo de veículo deseja que seja retornado." )@PathVariable TipoVeiculo tipoVeiculo){
        List<RegistroEstacionamentoResponseDTO> veiculosEncontrados = registroEstacionamentoService.exibirRegistrosTipo(tipoVeiculo);
        return ResponseEntity.ok(veiculosEncontrados);
    }

    @Operation(summary = "Exibe informações de um veículo específico.", description = "Retorna as informações salvas de um veículo específico de acordo com a sua placa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informações do veículo em específico encontrado com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    })
    @GetMapping("/placa/{placa}")
    public ResponseEntity<List<RegistroEstacionamentoResponseDTO>> exibirRegistrosPlaca(@Parameter(description = "Cliente informa a placa do carro específico que deseja retornar." )@PathVariable String placa){
        List<RegistroEstacionamentoResponseDTO> veiculosEncontrados = registroEstacionamentoService.exibirRegistrosPlaca(placa);
        return ResponseEntity.ok(veiculosEncontrados);
    }

    @Operation(summary = "Retorna todos os veículos presentes no estacionamento.", description = "Informa todos os veículos presentes no estacionamento no momento da requisição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "20o", description = "Veículos presentes no estacionamento encontrados com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro no servidor.")
    })
    @GetMapping("/presentes")
    public ResponseEntity<List<RegistroEstacionamentoResponseDTO>> exibirRegistrosPresentes(){
        List<RegistroEstacionamentoResponseDTO> veiculosEncontrados = registroEstacionamentoService.exibirRegistrosPresentes();
        return ResponseEntity.ok(veiculosEncontrados);
    }

    @Operation(summary = "Deleta um registro de veículo do sistema.", description = "Deleta um registro completo de um determinado veículo no sistema de acordo com o seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de veículo deletado com sucesso.."),
            @ApiResponse(responseCode = "404", description = "Nenhum registro de veículo encontrado com o ID informado.")
    })
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarRegistro(@Parameter(description = "Cliente informa o ID do registro em específico que deseja excluir.") @PathVariable Long id){
        boolean registroDeletado = registroEstacionamentoService.deletarRegistro(id);

        if (registroDeletado){
            return ResponseEntity.noContent().build();
        } return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("O registro de ID #" + id + " não foi encontrado!");
    }

}
