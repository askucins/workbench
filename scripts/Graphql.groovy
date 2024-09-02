@Grab('com.github.grooviter:gql-core:1.0.0')
import gql.DSL

def GraphQLFilm = DSL.type('Film') {
    field 'title', GraphQLString
    field 'year', GraphQLInt
}

def schema = DSL.schema {
    queries {
        field('lastFilm') {
            type GraphQLFilm
            staticValue(title: 'SPECTRE', year: 2015)
        }
    }
}

def query = """
  {
    lastFilm {
      year
      title
    }
  }
"""

def result = DSL.newExecutor(schema).execute(query)

assert result.data.lastFilm.year == 2015
assert result.data.lastFilm.title == 'SPECTRE'