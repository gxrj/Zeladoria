package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.DutyGroup;
import org.springframework.util.Assert;

import java.util.*;

public class DutyGroupTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var module = seeder.getServiceModule();
        var categoryService = module.getDutyGroupService();

        if( categoryService.countDutyGroups() == 0 ) {

            var dutyService = module.getDutyService();
            var categories = initializeMap();

            var list =  new ArrayList<DutyGroup>();

            categories.forEach(
                ( category, duties ) -> {

                    var dutyGroup = DutyGroup.builder()
                                                .name( category ).build();

                    if( !categoryService.createOrUpdate( dutyGroup ) )
                        throw new RuntimeException( "DutyGroup seeder failed!" );

                    duties.forEach(
                        duty -> {
                            var service = dutyService.findDutyByDescription( duty );
                            Assert.notNull( service, "Cannot find duty: "+
                                            duty+" to set its category while seeding" );
                            service.setDutyGroup( dutyGroup );
                            dutyService.createOrUpdate( service );
                        } );
                }
            );
        }
    }
    private static HashMap<String, List<String>> initializeMap() {

        var map = new HashMap<String, List<String>>();
        map.put(
                "Agua Pluvial, Bueiros e Esgoto",
                List.of(
                        "Alagamento", "Bueiro sem tampa", "Bueiro com tampa de madeira quebrado",
                        "Bueiro com tampa de ferro quebrado",
                        "Bueiro Entupido Internamente", "Esgoto",
                        "Falta de agua", "Vazamentos"
                ) );
        map.put(
                "Iluminacao e Energia",
                List.of( "Fiacao irregular", "Iluminacao publica", "Postes/Cabos" ) );
        map.put(
                "Infraestrutura Urbana",
                List.of( "Bloqueio de via", "Buracos na via", "Quebra-molas",
                        "Semaforo", "Sinalizacao", "Veiculo abandonado" ) );
        map.put(
                "Irregularidades",
                List.of(
                        "Ambulantes", "Condicao sanitaria irregular", "Estabelecimento irregular",
                        "Estacionamento irregular", "Emissao de poluentes", "Obra irregular",
                        "Ocupacao irregular", "Poluicao sonora", "Publicidade irregular",
                        "Coleta de lixo organico", "Mercado popular/quiosques",
                        "Publicidade irregular em via" ) );
        map.put(
                "Limpeza e Conservação",
                List.of(
                        "Capina e rocada", "Mato alto", "Coleta seletiva de lixo",
                        "Lixeiras publicas", "Entulho na calcada/via publica", "Manutencao de pracas",
                        "Limpeza de rua", "Retirada de galhos e restos de poda",
                        "Retirada de Animais mortos na via"
                ) );
        map.put(
                "Meio Ambiente",
                List.of(
                        "Maus tratos de animais", "Aterro sanitario irregular",
                        "Poda e retirada de arvores", "Queimada irregular",
                        "Poluicao de rios e lagos", "Desmatamento irregular",
                        "Animais na via", "Fiscalizacao ambiental", "Plantio de arvore"
                ) );
        map.put(
                "Pedestres e Ciclistas",
                List.of(
                        "Acessibilidade", "Bicicletario", "Faixa de pedestre",
                        "Passarela"
                ) );
        map.put(
                "Saúde",
                List.of(
                        "Saude publica", "Vigilancia sanitaria", "Foco de dengue",
                        "Fumace", "Exame", "Consulta", "Atendimento na unidade de saude"
                ) );
        map.put(
                "Servicos Públicos",
                List.of(
                        "Assistencia social", "Atendimento turistico", "Cultura",
                        "Escolas", "Esportes", "Obra publica", "Minha casa, minha vida",
                        "Maus tratos a idosos", "Violencia domestica contra mulher",
                        "Direitos humanos", "Abordagem de andarilho", "Bueiro Entupido Superficialmente"
                ) );
        map.put(
                "Transporte Público",
                List.of(
                        "Ponto de onibus danificado", "Transporte publico irregular",
                        "Onibus danificado", "Onibus superlotado", "Horario de onibus"
                ) );
        map.put(
                "Urbanismo",
                List.of(
                        "Equipamento publico danificado",
                        "Imovel abandonado",
                        "Patrimonio historico"
                ) );

        return map;
    }
}