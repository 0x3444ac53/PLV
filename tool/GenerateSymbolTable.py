import pyperclip
from terminaltables import GithubFlavoredMarkdownTable as mdt


def make_table(switch: str):
    byOperatation = [i.replace(';', '').strip() for i in switch.split('break')]
    tableVals = dict()
    tableList = []
    minimumLength = -1
    for i in byOperatation:
        if len(i.split()) == 0: continue
        minimumLength = max(
            len(unsanitizedOperators := list(
                filter(lambda x: x != 'case',
                       i.split()[:-1]))), minimumLength)
        tableVals[i.split()[-1][9:-1]] = list(
            map(lambda x: x[1:-2], [i for i in unsanitizedOperators]))
    for i in tableVals:
        tableVals[i].insert(0, f"**{i}**")

    return mdt([["**Operations**"] + ['--' for i in range(minimumLength)]
                 ] + [i for i in tableVals.values()]).table


pyperclip.copy(make_table(pyperclip.paste()))
