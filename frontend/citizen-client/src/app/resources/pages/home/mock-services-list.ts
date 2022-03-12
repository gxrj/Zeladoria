
/** This file mocks the server response of available services */

const mockServicesList = [
    { 
        category:'Água Pluvial, Bueiros e Esgoto',
        services: [
            'Alagamento',
            'Bueiro sem tampa',
            'Bueiro com tampa de madeira quebrado',
            'Bueiro com tampa de ferro quebrado',
            'Bueiro Entupido',
            'Esgoto',
            'Falta de água',
            'Vazamentos'
        ]
    },
    { 
        category:'Iluminação e Energia',
        services: [
            'Fiação irregular',
            'Iluminação pública',
            'Postes/Cabos'
        ]
    },
    { 
        category:'Infraestrutura Urbana',
        services: [
            'Bloqueio de via',
            'Buracos na via',
            'Quebra-molas',
            'Semáforo',
            'Sinalização',
            'Veículo abandonado'
        ]
    },
    { 
        category:'Irregularidades',
        services: [
            'Ambulantes',
            'Condição sanitária irregular',
            'Estabelecimento irregular',
            'Estacionamento irregular',
            'Emissão de poluentes',
            'Obra irregular',
            'Ocupação irregular',
            'Poluição sonora',
            'Publicidade irregular',
            'Coleta de lixo orgânico',
            'Mercado popular/quiosques'

        ]
    },
    { 
        category:'Limpeza e Conservação',
        services: [
            'Capina e roçada',
            'Mato alto',
            'Coleta seletiva de lixo',
            'Lixeiras públicas',
            'Entulho na calçada/via pública',
            'Manutenção de praças',
            'Limpeza de rua',
            'Retirada de galhos e restos de poda'
        ]
    },
    { 
        category:'Meio Ambiente',
        services: [
            'Maus tratos de animais',
            'Aterro sanitário irregular',
            'Poda e retirada de árvores',
            'Queimada irregular',
            'Poluição de rios e lagos',
            'Desmatamento irregular',
            'Animais na via',
            'Fiscalização ambiental',
            'Plantio de árvore'
        ]
    },
    { 
        category:'Pedestres e Ciclistas',
        services: [
            'Acessibilidade',
            'Bicicletário',
            'Faixa de pedestre',
            'Passarela'
        ]
    },
    { 
        category:'Saúde',
        services: [
            'Saúde pública',
            'Vigilância sanitária',
            'Foco de dengue',
            'Fumacê',
            'Exame',
            'Consulta',
            'Atendimento na unidade de saúde'
        ]
    },
    { 
        category:'Serviços Públicos',
        services: [
            'Assistência social',
            'Atendimento turístico',
            'Cultura',
            'Escolas',
            'Esportes',
            'Obra pública',
            'Minha casa, minha vida',
            'Maus tratos a idosos',
            'Violência doméstica contra mulher',
            'Direitos humanos',
            'Abordagem de andarilho'
        ]
    },
    { 
        category:'Transporte Público',
        services: [
            'Ponto de ônibus danificado',
            'Transporte público irregular',
            'Ônibus danificado',
            'Ônibus superlotado',
            'Horário de ônibus'
        ]
    },
    { 
        category:'Urbanismo',
        services: [
            'Equipamento público danificado',
            'Imóvel abandonado',
            'Patrimônio histórico'
        ]
    },
    { 
        category:'Pesquisa e Satisfação',
        services: []
    }
]

export default mockServicesList