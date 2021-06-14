# Maskit

Maskit é uma biblioteca para mascaramento de identificadores numéricos como CPF e CNPJ, por exemplo.

Ele é capaz de gerar uma versão mascarada e reversível de um identificador com o mesmo tamanho do original,
transformando seus dígitos em letras e/ou números de forma que não seja possível para uma pessoa inferir como isso foi feito.

Maskit não é uma técnica de criptografia forte e sim uma forma de pseudonimização voltada para uso em Logs e URLs ou simples descaracterização de dados.
Contudo, se usado de forma adequada, pode ser considerado uma forma razoável de anonimização ou pseudonimização de dados pessoais de acordo com a LGPD.

## Subprojetos

* maskit-core - Bilioteca que contém o núcleo do Maskit, incluindo as funcionalidades de "mask", "unmask" e "randomMask" básicas.
* maskservice - Aplicação que fornece vários serviços de mascaramento. Pode ser configurada para obter as chaves em um servidor (maskit-server) ou com configuração local.
* maskit-server - Aplicação que armazena e gerencia chaves de configuração de forma persistente para atender os serviços de mascaramento.
* maskit-lgpd - Bilioteca com funcionalidades voltadas para LGPD, incluindo Log (artigo 37) e mascaramento de CPF, CNPJ e demais números de identificação brasileiros.

## Conceitos Chave

* ID - Um número de identificação que precisa ser mascarado e/ou pseudonimizado
* Mascarado - Uma representação mascarada deste ID com letras e números. Indicado para Logs e seudonimização.
* Mascaramento - Processo de transformar um ID em um mascarado
* Desmascarado - Um mascarado que sofreu o processo inverso do mascaramento e mostra-se igual ao ID originaç
* Desmascaramento - Processo que obtém o ID original partir de um mascarado
* Mascarado Aleatório - Resultado do processo de mascaramento onde não é possível prever a saída. Indicado para URLs e tokens temporários.
* Chave de Configuração - Informações que o Maskit usa junto com seu algoritmo para efetuar suas operações de forma a personaliszar o funcionamento. Similar a chaves de criptografia.
* Sequência de Swap (chave 1) - Sequência numérica embaralhada usada para fazer a fase 1 do mascaramento: o embaralhamento (Swap). A sequência deve ter o mesmo número de elementos que o ID.
* Tabela de Caracteres (chave 2) - Conjunto de caracteres usados para converter (encode) os dígitos do ID original em caracteres gerais (letras e números)
* Padrão de mascaramento - Combinação do (1) algoritmo do Maskit com a (2) sequência de swap e a (3) tabela de caracteres gerando uma forma única realizar as operações de mascaramento.

### Operações

* mask - operação de mascaramento que transforma um ID num mascarado de forma idempotente, gerado sempre o mesmo mascarado dado um mesmo ID.
* randomMask - operação de mascaramento que gera um mascarado aleatório a partir de um ID,
* unmask - operação de transformar um mascarado no ID original independente se foi gerado por "mask" ou "randomMask"

## Características Principais (URISIS)

### Único
Os elementos mascarados gerados são únicos para um determinado ID original. Ou seja, dois IDs nunca gerarão mascarados iguais.
Mesmo para a operação de geração de mascarado aleatórios, tais mascarados só são gerados para o seu ID original.
Neste caso, inclusive, um ID de 11 dígitos, por exemplo, pode gerar milhões de mascarados aleatórios.
Todos únicos para este ID.

### Reversível
O elemento mascarado gerado pelo Maskit sempre pode ser revertido e voltar a ser o ID original antes do mascaramento.
O único pré-requisito é que seja usada a mesma sequência de swap (chave 1) e a mesma tabela de caracteres (chave 2).
Neste caso a reversão ocorre naturalmente mesmo para os mascarados gerados aleatoriamente (randomMask).

### Isométrico
O elemento mascarado gerado sempre terá o mesmo número de caracteres que o ID original.
O elemento mascarado, contudo, poderá ter letras e números misturados, dependendo da tabela de caracteres escolhida na configuração.

### Sensível
Pequenas alterações nos dígitos do ID original causam grandes mudanças no mascarado gerado.
A mudança de apenas um dígito gera uma mudança grande o suficiente para evitar que uma pessoa perceba o padrão de mascaramento utilizado.

### Idempotente
Um mesmo ID sempre gera um mesmo mascarado na operação "mask". Da mesma forma, um mascarado (oriundo de "mask" ou "randomMask") sempre é desmascarado para o mesmo ID.
O mascaramento de "mask" nunca gerará um valor diferente para uma mesma entrada.

## Exemplo de Uso

### Mascaramento Simples de CPF (Java)

O exemplo abaixo mostra como criar uma instância de Maskit a partir das duas chaves de configuração: (1) uma sequência de embaralhamento e (2) uma tabela de caracteres.
No exemplo um CPF é mascarado (método "mask") e desmascarado (método "unmask").

```java
package br.net.maskit.java;

import br.net.maskit.*;

import static br.net.maskit.Api.numericIdOf;
import static br.net.maskit.Api.numericMaskitOf;

public class JavaExemploSimples {

    public static void main(String[] args) {
        // Define uma sequência de Swap com indices embaralhados
        String seq = "7,9,1,5,0,10,6,2,3,8,4";
        // Define 60 caracteres da tabela     012345678901234567890123456789012345678901234567890123456789
        String table = "GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTc";
        // Cria uma instância de Maskit para Ids Numéricos (exemplo CPF)
        NumericMaskit maskit = numericMaskitOf(seq, table);
        // Cria uma versão mascarada do CPF (11 dígitos)
        Masked masked = maskit.mask(numericIdOf("12345678901"));
        System.out.println("CPF mascarado   :" + masked);
        NumericId unmasked = maskit.unmask(masked);
        System.out.println("CPF desmascarado:" + unmasked);
    }
}
```

### Mascaramento com Data/Hora Embutidos no Mascarado (Java)

Esta opção concatena a data e hora na frente do ID e mascara o texto resultante.
É possível desmascarar todo o texto ou obter o ID e a data/hora separadamente.

Também é possível escolher opções somente com data ou somente com hora, mas data/hora é o default.

```java
package br.net.maskit.java;

import br.net.maskit.Masked;
import br.net.maskit.NumericId;
import br.net.maskit.PrefixMaskit;

import java.time.LocalDateTime;

import static br.net.maskit.Api.numericIdOf;
import static br.net.maskit.Api.prefixMaskitOf;

public class JavaExemploPrefixo {
    public static void main(String[] args) {
        // Defina uma sequência de Swap com indices embaralhados
        // São 25 índices por causa do prefixo Data/Hora que tem 14 caracteres
        // 14 (data/hora) + 11 (cpf) = 25 índices
        String seq = "17,2,10,22,12,3,19,8,15,6,23,9,11,16,0,24,7,14,21,1,18,5,20,4,13";
        // Define 60 caracteres da tabela     012345678901234567890123456789012345678901234567890123456789
        String table = "GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTc";
        // Cria uma instância de Maskit que utiliza prefixos
        // PrefixMaskit maskit = prefixMaskitOf(seq, table, PrefixType.DATE_TIME); // opcional
        PrefixMaskit maskit = prefixMaskitOf(seq, table); // DATE_TIME é o default
        // Cria uma versão mascarada do CPF (25 dígitos: 14 da Data/Hora + 11 CPF)
        Masked masked = maskit.mask(numericIdOf("12345678901"));
        System.out.println("data/hora + CPF mascarados   :" + masked + " (25 dígitos)");
        // Número desmascarado com 25 dígitos onde os 14 primeiros s]ao data/hore
        NumericId unmasked = maskit.unmask(masked);
        System.out.println("Data/Hora + CPF desmascarados:" + unmasked +" (25 dígitos)");
        // Obtendo as duas partes do número (Data/hora e CPF)
        LocalDateTime ldt = (LocalDateTime) maskit.extractPrefixObj(masked);
        System.out.println("Data/Hora extraída :" + ldt);
        String cpfOriginal = maskit.extractId(masked).toString();
        System.out.println("CPF extraído       :" + cpfOriginal);
    }
}
```

### Mascaramento Simples de CPF (Kotlin)

Este exemplo é bem similar ao anterior, mas feito em Kotlin. O CPF é mascarado e desmascarado.

```kotlin
package br.net.maskit.example

import br.net.maskit.numericIdOf
import br.net.maskit.numericMaskitOf

fun main() {
    val sequence = "7,9,1,5,0,10,6,2,3,8,4";
    val table = "GoQd7ShKu2k9wONaC4Hr3mExBU0WgfFn5yP1pIvDLsM8iAtJe6RbVlXjYqTc"
    val maskit = numericMaskitOf(sequence, table)
    val id = numericIdOf("12345678901")
    val masked = maskit.mask(id)
    val unmasked = maskit.unmask(masked)
    println("Original ID: $id, masked: $masked, unmasked: $unmasked")
    //Original ID: 12345678901, masked: T3whm9JOPcU, unmasked: 12345678901
}
```

### Mascaramento com Chaves Aleatórias (Kotlin)

Neste exemplo as chaves de configuração não são informadas e sim geradas aleatoriamente.
Isso ocorre pela função "randomMaskit" que gera uma instância de Maskit com uma sequência de swap e uma tabela de caracteres geradas no momento da chamada da função.

ATENÇÂO: esta forma de uso não serve se você precisa pseudonimizar um ID. Cada vez que este método rodar as chaves serão diferentes e não será possível desmascarar IDs mascarados com chaves diferentes.
Este opção é recomendada apenas para situações onde os elementos mascarados são descartáveis como em mascaramento de URLs ou Tokens temporários.

```kotlin
package br.net.maskit.example

import br.net.maskit.numericIdOf
import br.net.maskit.randomMaskit

fun main() {
    val maskit = randomMaskit(11) // Id size = 11
    val id = numericIdOf("12345678901") // size = 11
    val masked = maskit.mask(id)
    val unmasked = maskit.unmask(masked)
    println("Original ID: $id, masked: $masked, unmasked: $unmasked")
}
```

### Geração de Tokens (Mascaramento com Chaves Aleatórias e Data/Hora) (Kotlin)

Neste exemplo o ID é mascarado para um elemento com data/hora embutido a partir de chaves geradas aleatoriamente.
Ou seja, é uma espécie de token temporário que costuma ser usado ao longo de uma sessão de usuário ou qualquer outro tempo mais curto.
Não há pretenção de identificar este ID mais tarde ou fazer busca por ele nos LOGs.
É um mascarado volátil, porém de difícil identificação justamente pelo seu curto tempo de vida e aleatoriedade das chaves.

```kotlin
package br.net.maskit.example

import br.net.maskit.numericIdOf
import br.net.maskit.randomPrefixMaskit

fun main() {
    val maskit = randomPrefixMaskit(25) // date/time (14) + Id (11) = 25
    val id = numericIdOf("12345678901") // Id size = 11
    val masked = maskit.mask(id) // masked size = 25!
    val unmasked = maskit.unmask(masked)
    println("Original ID: $id, masked: $masked, unmasked: $unmasked")
    println("Extracted ID       : ${maskit.extractId(masked)}")
    println("Extracted Date/Time: ${maskit.extractPrefixObj(masked)}")
}
```

