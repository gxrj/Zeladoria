package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.District;

import java.util.List;

public class DistrictTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var module = seeder.getServiceModule();

        var districtService = module.getDistrictService();

        if( districtService.countDistricts() == 0 ) {

            var districts = List.of(
                "Ajuda de Baixo", "Ajuda de Cima", "Alto dos Cajueiros",
                "Aroeira", "Bairro da Glória", "Barra de Macaé",
                "Botafogo", "Cabiúnas", "Cajueiros", "Campo D'Oeste",
                "Cancela Preta", "Cavaleiros", "Centro", "Costa do Sol",
                "Engenho da Praia", "Fronteira", "Granja dos Cavaleiros",
                "Horto", "Imbetiba", "Imboassica", "Jardim Santo Antonio",
                "Jardim Vitória", "Lagoa", "Lagomar", "Malvinas", "Miramar",
                "Mirante da Lagoa", "Nova Esperança", "Nova Holanda",
                "Novo Cavaleiros", "Novo Horizonte", "Parque Aeroporto",
                "Parque Atlantico", "Parque União", "Praia Campista",
                "Praia do Pecado", "Riviera Fluminense", "Sol Y Mar",
                "São José do Barreto", "São Marcos", "Vale Encantado",
                "Virgem Santa", "Visconde de Araújo"
            ).parallelStream().map( District::new ).toList();

            if( !districtService.createMultiple( districts ) )
                throw new RuntimeException( "DistrictTable seeder failed!" );
        }
    }
}
