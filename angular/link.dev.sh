pushd `dirname "${BASH_SOURCE[0]}"` > /dev/null
for d in *; do
	dep="$d/target/dependencies/angular-dependencies/"
	if [ -d $dep ]; then
		rm -rf $dep
	fi
	if [ -d $d ]; then
		mkdir -p $dep
		for f in *; do
			if [ -d $f ] && [ -d "$f/dist" ] && [[ $f != $d ]]; then
				for m in $f/dist/*; do
					if [ -d $m ]; then
						m=`readlink -f $m`
						ln -s $m $dep
					fi
				done
			fi
		done
	fi
done
popd > /dev/null
