package com.femass.resourceserver.init;

import com.femass.resourceserver.domain.Department;

import java.util.List;

public class DepartmentTableSeeder {

    public static void seed( TableSeeder seeder ) throws RuntimeException {

        var deptService = seeder.getServiceModule().getDepartmentService();

        if( deptService.countDepartments() == 0 ) {

            var departments = List.of(
                "Coordenadoria de Iluminação Pública",
                "Secretaria Adjunta de Saneamento",
                "Secretaria Adjunta de Serviços Públicos",
                "Secretaria De Infraestrutura",
                "Secretaria Adjunta de Defesa Civil",
                "Secretaria de Obras",
                "Secretaria de Mobilidade Urbana",
                "Secretaria de Ambiente e Sustentabilidade",
                "Secretaria de Cultura",
                "Secretaria de Esportes",
                "Secretaria de Ordem Pública",
                "Secretaria de Educação",
                "Coordenadoria Geral de Locações Imobiliárias",
                "Secretaria de Saúde",
                "Secretaria de Proteção Animal e Zoonoses",
                "Secretaria de Direitos Humanos e Acessibilidade",
                "Secretaria Adjunta de Habitação",
                "Secretaria do Idoso",
                "Inova Macae"
            ).parallelStream().map( Department::new ).toList();

            if( !deptService.createMultiple( departments ) )
                throw new RuntimeException( "Department seeder failed" );
        }
    }
}