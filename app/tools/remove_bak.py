#!/usr/bin/env python3
import os, shutil
root = os.path.join('app','src','main','res')
if not os.path.isdir(root):
    print('res folder not found:', root)
    raise SystemExit(1)
backup_dir = os.path.join('app','tools','res_bak_backups')
os.makedirs(backup_dir, exist_ok=True)
removed = []
for dirpath, dirnames, filenames in os.walk(root):
    for fn in filenames:
        if fn.endswith('.bak'):
            src = os.path.join(dirpath, fn)
            # preserve relative path
            rel = os.path.relpath(src, root)
            dst = os.path.join(backup_dir, rel)
            os.makedirs(os.path.dirname(dst), exist_ok=True)
            shutil.move(src, dst)
            removed.append(src)
print('Moved', len(removed), 'files to', backup_dir)
for p in removed:
    print(p)
