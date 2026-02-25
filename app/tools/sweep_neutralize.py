#!/usr/bin/env python3
import os, re, shutil, sys
root = os.path.join('app','src','main','res')
if not os.path.isdir(root):
    print('res folder not found:', root); sys.exit(1)

# replacements: pattern -> replacement (case-insensitive)
replacements = [
    (re.compile(r'WhatsApp', re.IGNORECASE), 'App'),
    (re.compile(r'Wa[\s-]?Enhancer', re.IGNORECASE), 'App Template'),
    (re.compile(r'WaEnhancer', re.IGNORECASE), 'App Template'),
    (re.compile(r'Xposed', re.IGNORECASE), 'framework'),
    (re.compile(r'LSPosed', re.IGNORECASE), 'framework'),
]

pkg_from = 'com.wmods.wppenhacer'
pkg_to = 'com.example.apptemplate'

changed = []

def repl_preserve(m, repl):
    s = m.group(0)
    # preserve capitalization heuristics
    if s.islower():
        return repl.lower()
    if s.isupper():
        return repl.upper()
    # Title-case or mixed -> return as-is replacement
    return repl

for dirpath, dirnames, filenames in os.walk(root):
    for fn in filenames:
        if not fn.endswith('.xml'):
            continue
        path = os.path.join(dirpath, fn)
        try:
            with open(path, 'r', encoding='utf-8') as f:
                orig = f.read()
        except Exception as e:
            print('skip', path, 'read error', e)
            continue
        text = orig
        if pkg_from in text:
            text = text.replace(pkg_from, pkg_to)
        for pattern, repl in replacements:
            text = pattern.sub(lambda m, r=repl: repl_preserve(m, r), text)
        if text != orig:
            bak = path + '.bak'
            if not os.path.exists(bak):
                try:
                    shutil.copy2(path, bak)
                except Exception:
                    pass
            try:
                with open(path, 'w', encoding='utf-8') as f:
                    f.write(text)
                changed.append(path)
            except Exception as e:
                print('failed write', path, e)

print('Modified', len(changed), 'files')
for p in changed:
    print(p)

# quick verification: search for remaining tokens
remain = []
checks = ['WhatsApp','Wa Enhancer','WaEnhancer','wppenhacer','Xposed','LSPosed']
for dirpath, dirnames, filenames in os.walk(root):
    for fn in filenames:
        if not fn.endswith('.xml'):
            continue
        path = os.path.join(dirpath, fn)
        try:
            with open(path, 'r', encoding='utf-8') as f:
                s = f.read()
        except Exception:
            continue
        for tok in checks:
            if tok in s:
                remain.append((path, tok))

if remain:
    print('\nRemaining occurrences:')
    for p,t in remain:
        print(p, '->', t)
    sys.exit(2)
else:
    print('\nNo remaining simple tokens found.')
    sys.exit(0)
