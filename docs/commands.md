# Команды
- `<arg>` - обязательные аргументы
- `[arg]` - опциональные аргументы
- `arg1|arg2` - варианты

## Общедоступные команды
#### `/rename [title|remove|clear|set <key> [:]] [text]`
Переименовывает предметы и изменяет им описание.
`/rename` - Выводит справку.
`/rename title <text>` - Устанавливает название предмета.
`/rename <text>` - Добавляет новую строку.
`/rename remove` - Удаляет последнюю строку.
`/rename clear` - Удаляет все описание.
`/rename set <key> : <value>` - Только для ГМов. Изменяет хар-ки предметов.

#### `/off <слова>` и `/g [слова]`
Оффтоп и глобал соответственно. Видимость глобала можно переключать через `/g` без слов.

#### `/lang [lang]`
Выводит все известные тебе языки или выбирает какой-то конкретный.

#### `/traits [player]`
Показывает твои трейты или трейты игрока.

#### `/unstuck`
Телепортирует игрока на один блок выше. Помогает при застревании.

## Команды ГМов
#### `/trait <trait> [player]`
Переключает указанный трейт для тебя или игрока.
Переключать трейты может только ГМ.

#### `/addict <drug_type> <values: 0 - 4> [player]`
Устанавливает степень зависимости игрока от наркотиков определенного типа.

Доступные значения (`values`):
* 0 - Зависимость отсутствует
* 1 - Зависимость, наркотик не принят
* 2 - Сильная зависимость, наркотик не принят
* 3 - Зависимость, наркотик принят
* 4 - Сильная зависимость, наркотик принят

#### `/gms`
Позволяет говорить как ГМ. Справку можно узнать по `/gms ?`.

#### `/susay <name> <text>`
Позовляет говорить от лица другого персонажа. В качестве языка используется выбранный язык ГМа.

#### `/meta <owner <value> | add <text> | remove | clear>]`
Редактирует мету предмета. Справку можно узнать по `/meta`.

#### `/aorta [reload]`
Может использоваться только админом или консолью.
`reload` - перезагружает конфиги Аорты.
