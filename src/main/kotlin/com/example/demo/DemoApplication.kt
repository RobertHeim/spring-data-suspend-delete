package com.example.demo

import kotlinx.coroutines.flow.Flow
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
  runApplication<DemoApplication>(*args)
}

data class Foo(
  @Id
  val id: Long? = null,
  val name: String,
)

interface FooRepository : CoroutineCrudRepository<Foo, Long> {
  /*suspend */ fun deleteByName(name: String)
}

@RestController
class FooController(
  private val repo: FooRepository,
) {

  @GetMapping
  suspend fun get(): Flow<Foo> {
    return repo.findAll()
  }

  @GetMapping("/{name}/add")
  suspend fun add(@PathVariable name: String): Foo {
    return repo.save(Foo(name = name))
  }

  @GetMapping("/{name}/delete")
  suspend fun delete(@PathVariable name: String) : String {
    repo.deleteByName(name)
    return "ok"
  }
}
