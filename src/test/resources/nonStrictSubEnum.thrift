namespace java org.thrift.ton.gen.nonStrictSubenum

enum Subenum {
	a = 2,
	b = 4
}

struct SubStruct {
	1: Subenum sube,
	2: i16 i16_n
}

struct Struktura {
	1: SubStruct sub
}

service SubServica {
	Struktura dajStrukturu();
}
