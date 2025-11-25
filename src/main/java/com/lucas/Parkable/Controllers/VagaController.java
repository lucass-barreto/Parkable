package com.lucas.Parkable.Controllers;

import com.lucas.Parkable.DTOs.Vaga.VagaRequestDTO;
import com.lucas.Parkable.DTOs.Vaga.VagaResponseDTO;
import com.lucas.Parkable.Service.VagasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Tag(name = "Gerenciamento das vagas", description = "Controller da API para gerenciamento de vagas")
@RestController
@RequestMapping("/vagas")
public class VagaController {

    @Autowired
    private VagasService vagasService;

    @Operation(summary = "Adicionar uma nova vaga", description = "Cadastra uma nova vaga no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vaga cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro no cadastro.")
    })
    @PostMapping("/adicionar")
    public ResponseEntity<VagaResponseDTO> adicionarVaga(@Parameter (description = "Usuário envia as informações necessárias para a nova vaga") @RequestBody VagaRequestDTO vagaRequestDTO) {
        VagaResponseDTO vagaAdicionada = vagasService.adicionarVaga(vagaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vagaAdicionada);
    }

    @Operation(summary = "Dashboard das vagas", description = "Lista todas as vagas cadastradas no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de todas as vagas cadastradas encontrada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro na comunicação com o servidor.")
    })
    @GetMapping("/dashboard")
    public ResponseEntity<List<VagaResponseDTO>> listarTodasVagas() {
        List<VagaResponseDTO> vagasDTO = vagasService.listarTodasVagas();
        return ResponseEntity.ok(vagasDTO);
    }


    @Operation(summary = "Retorna as vagas ocupadas no momento", description = "Lista todas as vagas que estão ocupadas no momento da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista com todas as vagas ocupadas encontrada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro na comunicação com o servidor.")
    })
    @GetMapping("/ocupadas")
    public ResponseEntity<List<VagaResponseDTO>> listarVagasOcupadas() {
        List<VagaResponseDTO> vagasEncontradas = vagasService.listarVagasOcupadas();
        return ResponseEntity.ok(vagasEncontradas);
    }

    @Operation(summary = "Retorna as vagas livres no momento", description = "Lista todas as vagas que estão livres no momento da requisição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista com todas as vagas livres encontrada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro na comunicação com o servidor.")
    })
    @GetMapping("/livres")
    public ResponseEntity<List<VagaResponseDTO>> listarVagasLivres() {
        List<VagaResponseDTO> vagasEncontradas = vagasService.listarVagasLivres();
        return ResponseEntity.ok(vagasEncontradas);
    }


    @Operation(summary = "Deletar vaga específica.", description = "Deleta uma vaga específica de acordo com o ID informado pelo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaga deletada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Vaga com o ID informado não foi encontrada.")
    })
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarVaga(@Parameter(description = "Usuário informa o ID da vaga respectiva que deseja deletar.") @PathVariable Long id) {
        boolean deletado = vagasService.deletarVaga(id);

        if (deletado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("A vaga de ID #" + id + " não foi encontrada!");
        }
    }
}

