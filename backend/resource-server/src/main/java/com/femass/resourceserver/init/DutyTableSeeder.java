package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.Duty;

import org.springframework.util.Assert;

import java.util.*;

public class DutyTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var module = seeder.getServiceModule();
        var dutyService = module.getDutyService();

        if( dutyService.countDuties() == 0 ) {

            var categories = initializeMap();
            var deptService = module.getDepartmentService();
            var dutyList = new ArrayList<Duty>();

            categories.forEach( ( deptId, duties ) -> {
                var dept = deptService.findDepartmentById( deptId );
                Assert.notNull( dept, "Department not found while seeding" );

                duties.forEach(
                        name -> dutyList.add(
                            Duty.builder().description( name ).department( dept ).build() ) );
            } );


            if( !dutyService.createMultiple( dutyList ) )
                throw new RuntimeException( "Duty seeder failed" );
        }
    }

    private static HashMap<Long, List<String>> initializeMap() {

        var map = new HashMap<Long, List<String>>();
        map.put( 1L, List.of( "Fiacao irregular", "Iluminacao publica", "Postes/Cabos" ) );
        map.put( 2L, List.of( "Bueiro Entupido Internamente", "Esgoto", "Falta de agua", "Vazamentos" ) );
        map.put( 3L, List.of( "Bueiro Entupido Superficialmente", "Buracos na via", "Capina e rocada",
                            "Mato alto", "Coleta seletiva de lixo", "Lixeiras publicas",
                            "Entulho na calcada/via publica", "Manutencao de pracas", "Limpeza de rua",
                            "Retirada de galhos e restos de poda", "Retirada de Animais mortos na via",
                            "Equipamento publico danificado" ) );
        map.put( 4L, List.of( "Bueiro sem tampa", "Bueiro com tampa de madeira quebrado",
                            "Bueiro com tampa de ferro quebrado" ) );
        map.put( 5L, List.of( "Alagamento" ) );
        map.put( 6L, List.of( "Obra irregular", "Obra publica" ) );
        map.put( 7L, List.of( "Bloqueio de via", "Quebra-molas", "Semaforo", "Sinalizacao",
                            "Publicidade irregular em via", "Veiculo abandonado",
                            "Acessibilidade", "Bicicletario", "Faixa de pedestre",
                            "Passarela", "Ponto de onibus danificado",
                            "Transporte publico irregular", "Onibus danificado",
                            "Onibus superlotado", "Horario de onibus" ) );
        map.put( 8L, List.of( "Emissao de poluentes", "Poluicao sonora", "Ocupacao irregular",
                            "Coleta de lixo organico", "Poda e retirada de arvores", "Aterro sanitario irregular",
                            "Queimada irregular", "Poluicao de rios e lagos", "Desmatamento irregular",
                            "Fiscalizacao ambiental", "Plantio de arvore" ) );
        map.put( 9L, List.of( "Atendimento turistico", "Cultura", "Patrimonio historico" ) );
        map.put( 10L, List.of( "Esportes" ) );
        map.put( 11L, List.of( "Ambulantes", "Estabelecimento irregular", "Estacionamento irregular",
                            "Mercado popular/quiosques", "Publicidade irregular",
                            "Violencia domestica contra mulher", "Abordagem de andarilho" ) );
        map.put( 12L, List.of( "Escolas" ) );
        map.put( 13L, List.of( "Imovel abandonado" ) );
        map.put( 14L, List.of( "Condicao sanitaria irregular", "Exame", "Saude publica",
                            "Vigilancia sanitaria", "Foco de dengue", "Fumace",  "Consulta",
                            "Atendimento na unidade de saude") );
        map.put( 15L, List.of( "Animais na via", "Maus tratos de animais" ) );
        map.put( 16L, List.of( "Assistencia social", "Direitos humanos") );
        map.put( 17L, List.of( "Minha casa, minha vida" ) );
        map.put( 18L, List.of( "Maus tratos a idosos" ) );

        return map;
    }
}